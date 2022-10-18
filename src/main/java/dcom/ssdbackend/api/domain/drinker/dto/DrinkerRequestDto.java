package dcom.ssdbackend.api.domain.drinker.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

public class DrinkerRequestDto {
    @ApiModel(value="사용자의 술잔 정보 수정")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DrinkerModify {
        private String name;
        private String detail;
        private Integer totalCapacity;
    }
}
