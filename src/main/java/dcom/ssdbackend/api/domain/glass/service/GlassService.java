package dcom.ssdbackend.api.domain.glass.service;

import dcom.ssdbackend.api.domain.glass.Glass;
import dcom.ssdbackend.api.domain.glass.dto.GlassRequestDto;
import dcom.ssdbackend.api.domain.glass.dto.GlassResponseDto;
import dcom.ssdbackend.api.domain.glass.repository.GlassRepository;
import dcom.ssdbackend.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GlassService {
    private final GlassRepository glassRepository;
    private final JwtProvider jwtProvider;

    public List<GlassResponseDto.GlassInfo> getGlass(){
        String drinkerToken = jwtProvider.getDrinkerTokenInHeader();
        boolean bool = jwtProvider.verifyDrinkerToken(drinkerToken);
        List<Glass> glasses = glassRepository.findAll();
        return GlassResponseDto.GlassInfo.of(glasses);
    }

    public void updateGlass(String glassId, GlassRequestDto.UpdateGlass updateGlass) {
        Glass glass = glassRepository.findById(glassId).get();

        glass.setDrinkerName(updateGlass.getDrinkerName());
        glass.setDetail(updateGlass.getDetail());
        glass.setTotalCapacity(updateGlass.getTotalCapacity());

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

    public void deleteGlass(String glassId) {
        glassRepository.deleteById(glassId);
    }
}
