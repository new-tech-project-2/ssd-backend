package dcom.ssdbackend.api.global.websocket;

import dcom.ssdbackend.api.domain.dispenser.service.DispenserSocketService;
import dcom.ssdbackend.api.domain.glass.service.GlassSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final GlassSocketService glassSocketService;
    private final DispenserSocketService dispenserSocketService;

    private static Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<WebSocketSession>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String payLoadMessage = message.getPayload();

        if (payLoadMessage.substring(0,9).equals("addGlass:")) {
           glassSocketService.addGlass(payLoadMessage,sessions);
        }

        if (payLoadMessage.substring(0,14).equals("drinkOneGlass:")) {
            glassSocketService.drinkOneGlass(payLoadMessage,sessions);
        }

        if(payLoadMessage.substring(0,15).equals("startDispenser:")){
            dispenserSocketService.dispenserStart(payLoadMessage,sessions);
        }

        if(payLoadMessage.substring(0,14).equals("stopDispenser:")){
            dispenserSocketService.dispenserStop(payLoadMessage,sessions);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }
}
