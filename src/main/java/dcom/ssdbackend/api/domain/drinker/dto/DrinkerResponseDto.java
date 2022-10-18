package dcom.ssdbackend.api.domain.drinker.dto;

import dcom.ssdbackend.api.domain.drinker.Drinker;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class DrinkerResponseDto {

    @ApiModel(value = "사용자의 술잔 등록")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DrinkerRegister {
        private Boolean success;
        private String authToken;

        public static DrinkerRegister of(Drinker drinker) {
            return DrinkerRegister.builder()
                    .success(true)
                    .authToken(drinker.getId())
                    .build();
        }
    }

    @ApiModel(value = "사용자의 술잔 정보 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DrinkerInfo{
        private String id;
        private String name;
        private String detail;
        private Integer totalCapacity;
        private Integer currentDrink;
        private Integer lastDrinkTimeStamp;
        private String dispenserId;

        public static DrinkerInfo of(Drinker drinker){
            return DrinkerInfo.builder()
                    .id(drinker.getId())
                    .name(drinker.getName())
                    .detail(drinker.getDetail())
                    .totalCapacity(drinker.getTotalCapacity())
                    .currentDrink(drinker.getCurrentDrink())
                    .lastDrinkTimeStamp(drinker.getLastDrinkTimeStamp())
                    .dispenserId(drinker.getDispenser().getId())
                    .build();
        }

        public static List<DrinkerInfo> of(List<Drinker> drinkers) {
            return drinkers.stream().map(DrinkerInfo::of).collect(Collectors.toList());
        }
}
}
