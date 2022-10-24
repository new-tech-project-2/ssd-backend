package dcom.ssdbackend.api.domain.glass.dto;

import dcom.ssdbackend.api.domain.glass.Glass;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class GlassResponseDto {

    @ApiModel(value = "술잔 등록")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GlassRegister {
        private Boolean success;
        private String drinkerToken;

        public static GlassRegister of(Glass glass) {
            return GlassRegister.builder()
                    .success(true)
                    .drinkerToken(glass.getDrinkerName())
                    .build();
        }
    }

    @ApiModel(value = "술잔 정보 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GlassInfo{
        private String id;
        private String drinkerName;
        private String detail;
        private Integer totalCapacity;
        private Integer currentDrink;
        private Integer lastDrinkTimeStamp;
        private String dispenserId;

        public static GlassInfo of(Glass glass){
            return GlassInfo.builder()
                    .id(glass.getId())
                    .drinkerName(glass.getDrinkerName())
                    .detail(glass.getDetail())
                    .totalCapacity(glass.getTotalCapacity())
                    .currentDrink(glass.getCurrentDrink())
                    .lastDrinkTimeStamp(glass.getLastDrinkTimeStamp())
                    .dispenserId(glass.getDispenser().getId())
                    .build();
        }

        public static List<GlassInfo> of(List<Glass> glasses) {
            return glasses.stream().map(GlassInfo::of).collect(Collectors.toList());
        }
}
}
