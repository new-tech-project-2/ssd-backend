package dcom.ssdbackend.api.domain.glass.service;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import dcom.ssdbackend.api.domain.dispenser.repository.DispenserRepository;
import dcom.ssdbackend.api.domain.glass.Glass;
import dcom.ssdbackend.api.domain.glass.dto.GlassRequestDto;
import dcom.ssdbackend.api.domain.glass.dto.GlassResponseDto;
import dcom.ssdbackend.api.domain.glass.repository.GlassRepository;
import dcom.ssdbackend.api.global.jwt.JwtProvider;
import dcom.ssdbackend.api.global.websocket.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GlassService {
    private final GlassRepository glassRepository;
    private final DispenserRepository dispenserRepository;
    private final JwtProvider jwtProvider;


    public void addGlass(GlassRequestDto.AddGlass addGlass) throws IOException{

        if(!glassRepository.existsById(addGlass.getGlassId())) {
            Glass glass = new Glass();
            Dispenser dispenser = dispenserRepository.findById(addGlass.getDispenserId()).get();

            glass.setId(addGlass.getGlassId());
            glass.setDrinkerName("exampleDrinkerName");
            glass.setDetail("exampleDetail");
            glass.setTotalCapacity(0);
            glass.setCurrentDrink(0);
            glass.setLastDrinkTimeStamp(0L);
            glass.setDispenser(dispenser);

            glassRepository.save(glass);

            List<WebSocketSession> drinkerWebSocketSessionList = WebSocketHandler.drinkerMap.get(glass.getDispenser().getId());

            if (!drinkerWebSocketSessionList.isEmpty()) {
                for (WebSocketSession s : drinkerWebSocketSessionList) {
                    s.sendMessage(new TextMessage("{\"eventType\":\"change\"}"));
                }
            }
        }
    }

    public List<GlassResponseDto.GlassInfo> getGlass(){
        List<Glass> glasses = glassRepository.findAll();
        return GlassResponseDto.GlassInfo.of(glasses);
    }

    public void updateGlass(String glassId, GlassRequestDto.UpdateGlass updateGlass) throws IOException {
        Glass glass = glassRepository.findById(glassId).get();

        glass.setDrinkerName(updateGlass.getDrinkerName());
        glass.setDetail(updateGlass.getDetail());
        glass.setTotalCapacity(updateGlass.getTotalCapacity());

        glassRepository.save(glass);

        List<WebSocketSession> drinkerWebSocketSessionList = WebSocketHandler.drinkerMap.get(glass.getDispenser().getId());

        if(!drinkerWebSocketSessionList.isEmpty()) {
            for (WebSocketSession s : drinkerWebSocketSessionList) {
                s.sendMessage(new TextMessage("{\"eventType\":\"change\"}"));
            }
        }
    }

    public void deleteGlass(String glassId) throws IOException {
        Glass glass = glassRepository.findById(glassId).get();

        List<WebSocketSession> drinkerWebSocketSessionList = WebSocketHandler.drinkerMap.get(glass.getDispenser().getId());

        glassRepository.delete(glass);

        if(!drinkerWebSocketSessionList.isEmpty()) {
            for (WebSocketSession s : drinkerWebSocketSessionList) {
                s.sendMessage(new TextMessage("{\"eventType\":\"change\"}"));
            }
        }
    }

    public void drinkOneGlass(String glassId) throws IOException{
        Glass glass = glassRepository.findById(glassId).get();

        List<WebSocketSession> drinkerWebSocketSessionList = WebSocketHandler.drinkerMap.get(glass.getDispenser().getId());

        glass.setCurrentDrink(glass.getCurrentDrink() + 1);
        glass.setLastDrinkTimeStamp(System.currentTimeMillis());
        glassRepository.save(glass);

        if(!drinkerWebSocketSessionList.isEmpty()) {
            for (WebSocketSession s : drinkerWebSocketSessionList) {
                s.sendMessage(new TextMessage("{\"eventType\":\"change\"}"));
            }
        }
    }
}
