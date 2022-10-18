package dcom.ssdbackend.api.domain.dispenser.service;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import dcom.ssdbackend.api.domain.dispenser.dto.DispenserResponseDto;
import dcom.ssdbackend.api.domain.dispenser.repository.DispenserRepository;
import dcom.ssdbackend.api.domain.drinker.service.DrinkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DispenserService {

    private final DispenserRepository dispenserRepository;

    private final DrinkerService drinkerService;

    public DispenserResponseDto.DispenserInfo dispenserInfo(){
        String dispenserId = drinkerService.getAuthTokenInHeader();
        Dispenser dispenser = dispenserRepository.findById(dispenserId).get();
        return DispenserResponseDto.DispenserInfo.of(dispenser);
    }

    public void dispenserStart(){
        String dispenserId = drinkerService.getAuthTokenInHeader();
        Dispenser dispenser = dispenserRepository.findById(dispenserId).get();

            dispenser.setStarted(true);

            dispenser.builder()
                    .id(dispenser.getId())
                    .started(dispenser.isStarted())
                    .build();

            dispenserRepository.save(dispenser);
    }

    public void dispenserStop(){
        String dispenserId = drinkerService.getAuthTokenInHeader();
        Dispenser dispenser = dispenserRepository.findById(dispenserId).get();

        dispenser.setStarted(false);

        dispenser.builder()
                .id(dispenser.getId())
                .started(dispenser.isStarted())
                .build();

        dispenserRepository.save(dispenser);
    }
}
