package dcom.ssdbackend.api.global.websocket;

import dcom.ssdbackend.api.domain.dispenser.service.DispenserSocketService;
import dcom.ssdbackend.api.domain.glass.service.GlassSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.*;

@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final GlassSocketService glassSocketService;
    private final DispenserSocketService dispenserSocketService;

    private static Map<WebSocketSession,String> sessions = new HashMap<WebSocketSession,String>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payLoadMessage = message.getPayload();

        if (payLoadMessage.substring(0, 25).equals("addGlassIdAndDispenserId:")) {
                glassSocketService.addGlass(session, payLoadMessage, sessions);
        }

        if (payLoadMessage.substring(0, 14).equals("drinkOneGlass:")) {
            glassSocketService.drinkOneGlass(session, payLoadMessage, sessions);
        }

        if (payLoadMessage.substring(0, 15).equals("startDispenser:")) {
            dispenserSocketService.startDispenser(session, payLoadMessage, sessions);
        }

        if (payLoadMessage.substring(0, 14).equals("stopDispenser:")) {
            dispenserSocketService.stopDispenser(session, payLoadMessage, sessions);
        }
    }

    @Override
    public void afterConnectionClosed (WebSocketSession session, CloseStatus status){
        sessions.remove(session);
    }
}
