package dcom.ssdbackend.api.domain.glass.service;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import dcom.ssdbackend.api.domain.dispenser.repository.DispenserRepository;
import dcom.ssdbackend.api.domain.glass.Glass;
import dcom.ssdbackend.api.domain.glass.dto.GlassRequestDto;
import dcom.ssdbackend.api.domain.glass.dto.GlassResponseDto;
import dcom.ssdbackend.api.domain.glass.repository.GlassRepository;
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
public class GlassService {

    private final HttpServletRequest request;
    private final GlassRepository glassRepository;
    private final DispenserRepository dispenserRepository;

    public String getDispenserTokenInHeader(){
        String dispenserToken = request.getHeader("dispenserToken");

        if(dispenserToken==null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized Request");
        return dispenserToken;
    }

    public String getDrinkerTokenInHeader(){
        String drinkerToken = request.getHeader("drinkerToken");

        if(drinkerToken==null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized Request");
        return drinkerToken;
    }

    public Glass getCurrentGlass(){
        return glassRepository.findById(getDrinkerTokenInHeader()).get();
    }

    public GlassResponseDto.GlassRegister glassRegister(){
        Optional<Dispenser> dispenser = dispenserRepository.findById(getDispenserTokenInHeader());
        if(!getDispenserTokenInHeader().equals("dispenser")){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, " Unauthorized Request");
        }
        else{
            Glass glass = new Glass();
            glass.setId(UUID.randomUUID().toString());
            glass.setDispenser(dispenser.get());
            glassRepository.save(glass);

            return GlassResponseDto.GlassRegister.of(glass);
        }
    }

    public List<GlassResponseDto.GlassInfo> glassInfo(){
        List<Glass> glasses = glassRepository.findAll();
        return GlassResponseDto.GlassInfo.of(glasses);
    }

    public void glassModify(String glassId, GlassRequestDto.GlassModify glassModify) {
        Glass glass = glassRepository.findById(glassId).get();

        glass.setDrinkerName(glassModify.getDrinkerName());
        glass.setDetail(glassModify.getDetail());
        glass.setTotalCapacity(glassModify.getTotalCapacity());

        glass.builder()
                .id(glass.getId())
                .drinkerName(glass.getDrinkerName())
                .detail(glass.getDetail())
                .totalCapacity(glass.getTotalCapacity())
                .currentDrink(glass.getCurrentDrink())
                .lastDrinkTimeStamp(glass.getLastDrinkTimeStamp())
                .dispenser(glass.getDispenser())
                .build();

        glassRepository.save(glass);
    }

    public void glassRemove(String glassId) {
        glassRepository.deleteById(glassId);
    }
}
