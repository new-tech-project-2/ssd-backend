package dcom.ssdbackend.api.domain.drinker.service;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import dcom.ssdbackend.api.domain.dispenser.repository.DispenserRepository;
import dcom.ssdbackend.api.domain.drinker.Drinker;
import dcom.ssdbackend.api.domain.drinker.dto.DrinkerRequestDto;
import dcom.ssdbackend.api.domain.drinker.dto.DrinkerResponseDto;
import dcom.ssdbackend.api.domain.drinker.repository.DrinkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DrinkerService {

    private final HttpServletRequest request;
    private final DrinkerRepository drinkerRepository;
    private final DispenserRepository dispenserRepository;

    public String getAuthTokenInHeader(){
        String authToken = request.getHeader("authToken");

        if(authToken==null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized Request");
        return authToken;
    }

    public Drinker getCurrentDrinker(){
        return drinkerRepository.findById(getAuthTokenInHeader()).get();
    }

    public DrinkerResponseDto.DrinkerRegister drinkerRegister(){
        Optional<Dispenser> dispenser = dispenserRepository.findById(getAuthTokenInHeader());
        if(!getAuthTokenInHeader().equals("dispenser")){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, " Unauthorized Request");
        }
        else{
            Drinker drinker = new Drinker();
            drinker.setId(UUID.randomUUID().toString());
            drinker.setDispenser(dispenser.get());
            drinkerRepository.save(drinker);

            return DrinkerResponseDto.DrinkerRegister.of(drinker);
        }
    }

    public List<DrinkerResponseDto.DrinkerInfo> drinkerInfo(){
        List<Drinker> drinkers = drinkerRepository.findAll();
        return DrinkerResponseDto.DrinkerInfo.of(drinkers);
    }

    public void drinkerModify(String drinkerId,DrinkerRequestDto.DrinkerModify drinkerModify) {
        Drinker drinker = drinkerRepository.findById(drinkerId).get();

        drinker.setName(drinkerModify.getName());
        drinker.setDetail(drinkerModify.getDetail());
        drinker.setTotalCapacity(drinkerModify.getTotalCapacity());

        drinker.builder()
                .id(drinker.getId())
                .name(drinker.getName())
                .detail(drinker.getDetail())
                .totalCapacity(drinker.getTotalCapacity())
                .currentDrink(drinker.getCurrentDrink())
                .lastDrinkTimeStamp(drinker.getLastDrinkTimeStamp())
                .dispenser(drinker.getDispenser())
                .build();

        drinkerRepository.save(drinker);
    }

    public void drinkerRemove(String drinkerId) {
        drinkerRepository.deleteById(drinkerId);
    }
}
