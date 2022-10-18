package dcom.ssdbackend.api.domain.dispenser.dto;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import dcom.ssdbackend.api.domain.drinker.Drinker;
import dcom.ssdbackend.api.domain.drinker.dto.DrinkerResponseDto;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class DispenserResponseDto {

    @ApiModel(value = "디스펜서 정보 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DispenserInfo {
        private String id;
        private Boolean started;

        public static DispenserResponseDto.DispenserInfo of(Dispenser dispenser) {
            return DispenserInfo.builder()
                    .id(dispenser.getId())
                    .started(dispenser.isStarted())
                    .build();
        }
    }
}
