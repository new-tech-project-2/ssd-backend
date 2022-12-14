package dcom.ssdbackend.api.global.websocket;

import dcom.ssdbackend.api.domain.dispenser.service.DispenserSocketService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final DispenserSocketService dispenserSocketService;

    public static Map<String,List<WebSocketSession>> drinkerMap = new HashMap<String,List<WebSocketSession>>();
    public static Map<String,WebSocketSession> keyDispenserMap = new HashMap<String,WebSocketSession>();
    public static Map<WebSocketSession,String> valueDispenserMap = new HashMap<WebSocketSession,String>();
    public static Map<WebSocketSession, String> keyDrinkerMap = new HashMap<WebSocketSession,String>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payLoadMessage = message.getPayload();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(payLoadMessage);

        if(jsonObject.get("eventType").equals("dispenserLogin")){
            dispenserSocketService.dispenserLogin(session,(String)jsonObject.get("dispenserId"),drinkerMap,keyDispenserMap,valueDispenserMap);
        }

        if(jsonObject.get("eventType").equals("drinkerLogin")){
            dispenserSocketService.drinkerLogin(session,(String)jsonObject.get("dispenserId"),drinkerMap,keyDrinkerMap);
        }

        if(jsonObject.get("eventType").equals("drinkerLogout")){
            dispenserSocketService.drinkerLogout(session,(String)jsonObject.get("dispenserId"),drinkerMap,keyDrinkerMap);
        }

        if(jsonObject.get("eventType").equals("startDispenser")){
            dispenserSocketService.startDispenser((String)jsonObject.get("dispenserId"),drinkerMap,keyDispenserMap);
        }

        if(jsonObject.get("eventType").equals("stopDispenser")){
            dispenserSocketService.stopDispenser((String)jsonObject.get("dispenserId"),drinkerMap,keyDispenserMap);
        }
    }

    @Override
    public void afterConnectionClosed (WebSocketSession session, CloseStatus status) throws IOException {
        if (keyDispenserMap.containsValue(session)) {
            String dispenserId = valueDispenserMap.get(session);

            if (!drinkerMap.get(dispenserId).isEmpty()) {
                for (WebSocketSession s : drinkerMap.get(dispenserId)) {
                    s.sendMessage(new TextMessage("{\"eventType\":\"end\"}"));
                    keyDrinkerMap.remove(s);
                    s.close();
                }
            }
            drinkerMap.remove(dispenserId);
            keyDispenserMap.remove(dispenserId);
            valueDispenserMap.remove(session);

        } else if(keyDrinkerMap.keySet().contains(session)) {
            String dispenserId = keyDrinkerMap.get(session);
            drinkerMap.get(dispenserId).remove(session);
            keyDrinkerMap.remove(session);
        }
    }
}
