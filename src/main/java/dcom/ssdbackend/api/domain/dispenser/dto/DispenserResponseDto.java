package dcom.ssdbackend.api.domain.dispenser.dto;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import io.swagger.annotations.ApiModel;
import lombok.*;

public class DispenserResponseDto {
    @ApiModel(value = "음주자 토큰 발급")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DrinkerLogin {
        private Boolean success;
        private String drinkerToken;
    }

    @ApiModel(value = "디스펜서 정보 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DispenserInfo {
        private String dispenserId;
        private Boolean started;

        public static DispenserResponseDto.DispenserInfo of(Dispenser dispenser) {
            return DispenserInfo.builder()
                    .dispenserId(dispenser.getId())
                    .started(dispenser.isStarted())
                    .build();
        }
    }
}
