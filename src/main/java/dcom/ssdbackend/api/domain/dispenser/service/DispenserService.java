package dcom.ssdbackend.api.domain.dispenser.service;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import dcom.ssdbackend.api.domain.dispenser.dto.DispenserResponseDto;
import dcom.ssdbackend.api.domain.dispenser.repository.DispenserRepository;
import dcom.ssdbackend.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DispenserService {
    private final DispenserRepository dispenserRepository;
    private final JwtProvider jwtProvider;

    public DispenserResponseDto.DispenserInfo getDispenser(){
        String dispenserId = jwtProvider.getDispenserToken(jwtProvider.getDrinkerTokenInHeader());
        Dispenser dispenser = dispenserRepository.findById(dispenserId).get();
        return DispenserResponseDto.DispenserInfo.of(dispenser);
    }
}
