package dcom.ssdbackend.api.domain.dispenser.service;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import dcom.ssdbackend.api.domain.dispenser.repository.DispenserRepository;
import dcom.ssdbackend.api.domain.glass.Glass;
import dcom.ssdbackend.api.domain.glass.repository.GlassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class DispenserSocketService {
    private final DispenserRepository dispenserRepository;
    private final GlassRepository glassRepository;

    public void dispenserLogin(WebSocketSession session, String dispenserId, Map<String, List<WebSocketSession>> drinkerMap, Map<String, WebSocketSession> keyDispenserMap, Map<WebSocketSession, String> valueDispenserMap) {
        drinkerMap.put(dispenserId, new ArrayList<>());
        keyDispenserMap.put(dispenserId, session);
        valueDispenserMap.put(session, dispenserId);
    }

    public void drinkerLogin(WebSocketSession session, String dispenserId, Map<String, List<WebSocketSession>> drinkerMap, Map<WebSocketSession, String> keyDrinkerMap) {
        List<WebSocketSession> drinkerWebSocketSessionList = drinkerMap.get(dispenserId);
        drinkerWebSocketSessionList.add(session);
        keyDrinkerMap.put(session,dispenserId);
    }

    public void startDispenser(String dispenserId, Map<String, List<WebSocketSession>> drinkerMap, Map<String, WebSocketSession> keyDispenserMap) throws IOException {
        Dispenser dispenser = dispenserRepository.findById(dispenserId).get();

        dispenser.setStarted(true);

        dispenserRepository.save(dispenser);

        List<WebSocketSession> drinkerWebSocketSessionList = drinkerMap.get(dispenserId);
        WebSocketSession dispenserWebSocketSession = keyDispenserMap.get(dispenserId);

        if (!drinkerWebSocketSessionList.isEmpty() && keyDispenserMap.containsKey(dispenserId)) {
            for (WebSocketSession s : drinkerWebSocketSessionList) {
                s.sendMessage(new TextMessage("{\"eventType\":\"start\"}"));
            }

            dispenserWebSocketSession.sendMessage(new TextMessage("{\"eventType\":\"start\"}"));
        }
    }

    public void stopDispenser(String dispenserId, Map<String, List<WebSocketSession>> drinkerMap, Map<String, WebSocketSession> keyDispenserMap) throws IOException {
        Dispenser dispenser = dispenserRepository.findById(dispenserId).get();
        List<Glass> glasses = glassRepository.findByDispenser(dispenser);

        dispenser.setStarted(false);

        dispenserRepository.save(dispenser);

        List<WebSocketSession> drinkerWebSocketSessionList = drinkerMap.get(dispenserId);
        WebSocketSession dispenserWebSocketSession = keyDispenserMap.get(dispenserId);

        if (!drinkerWebSocketSessionList.isEmpty() && keyDispenserMap.containsKey(dispenserId)) {
            for (WebSocketSession s : drinkerWebSocketSessionList) {
                s.sendMessage(new TextMessage("{\"eventType\":\"stop\"}"));
            }

            dispenserWebSocketSession.sendMessage(new TextMessage("{\"eventType\":\"stop\"}"));

            for(Glass g : glasses){
                g.setCurrentDrink(0);
                g.setLastDrinkTimeStamp(0L);

                glassRepository.save(g);
            }
        }
    }
}
