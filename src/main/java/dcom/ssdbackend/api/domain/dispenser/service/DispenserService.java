package dcom.ssdbackend.api.domain.dispenser.service;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import dcom.ssdbackend.api.domain.dispenser.dto.DispenserResponseDto;
import dcom.ssdbackend.api.domain.dispenser.repository.DispenserRepository;
import dcom.ssdbackend.api.global.jwt.JwtProvider;
import dcom.ssdbackend.api.global.websocket.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DispenserService {
    private final DispenserRepository dispenserRepository;
    private final JwtProvider jwtProvider;


    public DispenserResponseDto.DrinkerLogin drinkerLogin(){
        Dispenser dispenser = dispenserRepository.findById(jwtProvider.getDispenserTokenInHeader()).get();
        String drinkerToken = jwtProvider.generateDrinkerToken(dispenser.getId());

        return DispenserResponseDto.DrinkerLogin.builder()
                .success(true)
                .drinkerToken(drinkerToken)
                .build();
    }

    public DispenserResponseDto.DispenserInfo getDispenser(){
        String dispenserId = jwtProvider.getDispenserToken(jwtProvider.getDrinkerTokenInHeader());
        Dispenser dispenser = dispenserRepository.findById(dispenserId).get();
        return DispenserResponseDto.DispenserInfo.of(dispenser);
    }
}
