package dcom.ssdbackend.api.domain.dispenser.service;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import dcom.ssdbackend.api.domain.dispenser.repository.DispenserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class DispenserSocketService {
    private final DispenserRepository dispenserRepository;

    public void startDispenser(WebSocketSession session,String payLoadMessage, Map<WebSocketSession,String> sessions) throws Exception{
        // startDispenser:{dispenserId}
        String dispenserId = payLoadMessage.substring(15);
        Dispenser dispenser = dispenserRepository.findById(dispenserId).get();

        dispenser.setStarted(true);

        dispenserRepository.save(dispenser);

        for (WebSocketSession s : sessions.keySet()) {
            if (sessions.get(s).equals(dispenserId)) {
                s.sendMessage(new TextMessage("술자리가 시작되었습니다!"));
            }
        }
    }

    public void stopDispenser(WebSocketSession session,String payLoadMessage, Map<WebSocketSession,String> sessions) throws Exception{
        // stopDispenser:{dispenserId}
        String dispenserId = payLoadMessage.substring(14);
        Dispenser dispenser = dispenserRepository.findById(dispenserId).get();

        dispenser.setStarted(false);

        dispenserRepository.save(dispenser);

        for (WebSocketSession s : sessions.keySet()) {
            if (sessions.get(s).equals(dispenserId)) {
                s.sendMessage(new TextMessage("술자리가 종료되었습니다!"));
            }
        }
    }
}
