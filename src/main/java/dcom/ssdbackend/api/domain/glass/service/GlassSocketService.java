package dcom.ssdbackend.api.domain.glass.service;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import dcom.ssdbackend.api.domain.dispenser.repository.DispenserRepository;
import dcom.ssdbackend.api.domain.glass.Glass;
import dcom.ssdbackend.api.domain.glass.repository.GlassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GlassSocketService {
    private final GlassRepository glassRepository;
    private final DispenserRepository dispenserRepository;

    public void addGlass(String payLoadMessage, Set<WebSocketSession> sessions) throws Exception{
        String glassId = payLoadMessage.substring(9); // 앞에 addGlass: 붙음

        Glass glass = new Glass();
        Dispenser dispenser = dispenserRepository.findById("dispenser01").get();

        glass.setId(glassId);
        glass.setTotalCapacity(0);
        glass.setCurrentDrink(0);
        glass.setDispenser(dispenser);
        glassRepository.save(glass);

        for (WebSocketSession s : sessions) {
            s.sendMessage(new TextMessage(glassId + "가 등록되었습니다!"));
        }
    }

    public void drinkOneGlass(String payLoadMessage, Set<WebSocketSession> sessions) throws Exception {
        String glassId = payLoadMessage.substring(14); // 앞에 drinkOneGlass: 붙음
        Glass glass = glassRepository.findById(glassId).get();
        Dispenser dispenser = dispenserRepository.findById(glass.getDispenser().getId()).get();

        if (dispenser.isStarted()) {
            for (WebSocketSession s : sessions) {
                s.sendMessage(new TextMessage(glass.getDrinkerName() + "님이 한잔 마십니다!"));
            }

            glass.setCurrentDrink(glass.getCurrentDrink() + 1);
            glass.setLastDrinkTimeStamp(System.currentTimeMillis());
            glassRepository.save(glass);

            if (glass.getCurrentDrink() >= glass.getTotalCapacity()) {
                for (WebSocketSession s : sessions) {
                    s.sendMessage(new TextMessage(glass.getDrinkerName() + "님이 과음중 입니다!"));
                }
            }
        }
        else{
            for (WebSocketSession s : sessions) {
                s.sendMessage(new TextMessage("술자리가 아직 시작되지 않았습니다!"));
            }
        }
    }
}
