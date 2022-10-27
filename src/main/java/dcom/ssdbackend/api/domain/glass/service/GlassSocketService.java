package dcom.ssdbackend.api.domain.glass.service;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import dcom.ssdbackend.api.domain.dispenser.repository.DispenserRepository;
import dcom.ssdbackend.api.domain.glass.Glass;
import dcom.ssdbackend.api.domain.glass.repository.GlassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class GlassSocketService {
    private final GlassRepository glassRepository;
    private final DispenserRepository dispenserRepository;

    public void addGlass(WebSocketSession session, String payLoadMessage, Map<WebSocketSession, String> sessions) throws Exception {
        // addGlassIdAndDispenserId:{glassId}-{dispenserId}
        String glassId = payLoadMessage.substring(25, 56);
        String dispenserId = payLoadMessage.substring(57);

        Glass glass = new Glass();
        Dispenser dispenser = dispenserRepository.findById(dispenserId).get();

        glass.setId(glassId);
        glass.setTotalCapacity(0);
        glass.setCurrentDrink(0);
        glass.setDispenser(dispenser);
        glassRepository.save(glass);

        sessions.put(session, dispenserId);

        for (WebSocketSession s : sessions.keySet()) {
            if (sessions.get(s).equals(dispenserId)) {
                s.sendMessage(new TextMessage(dispenserId + "에 술잔이 등록되었습니다!"));
            }
        }
    }

    public void drinkOneGlass(WebSocketSession session, String payLoadMessage, Map<WebSocketSession, String> sessions) throws Exception {
        // drinkOneGlass:{glassId}
        String glassId = payLoadMessage.substring(14);
        Glass glass = glassRepository.findById(glassId).get();
        Dispenser dispenser = dispenserRepository.findById(glass.getDispenser().getId()).get();

        if (dispenser.isStarted()) {
            for (WebSocketSession s : sessions.keySet()) {
                if (sessions.get(s).equals(dispenser.getId())) {
                    s.sendMessage(new TextMessage(glass.getDrinkerName() + "님이 한잔 마십니다!"));
                }
            }

            glass.setCurrentDrink(glass.getCurrentDrink() + 1);
            glass.setLastDrinkTimeStamp(System.currentTimeMillis());
            glassRepository.save(glass);

            if (glass.getCurrentDrink() >= glass.getTotalCapacity()) {
                for (WebSocketSession s : sessions.keySet()) {
                    if (sessions.get(s).equals(dispenser.getId())) {
                        s.sendMessage(new TextMessage(glass.getDrinkerName() + "님이 과음중 입니다!"));
                    }
                }
            }
        }
        else {
            for (WebSocketSession s : sessions.keySet()) {
                if (sessions.get(s).equals(dispenser.getId())) {
                    s.sendMessage(new TextMessage("술자리가 시작되지 않았습니다!"));
                }
            }
        }
    }
}
