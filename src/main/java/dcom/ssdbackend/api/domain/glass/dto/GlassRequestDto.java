package dcom.ssdbackend.api.domain.glass.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

public class GlassRequestDto {
    @ApiModel(value="사용자의 술잔 정보 수정")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GlassModify {
        private String drinkerName;
        private String detail;
        private Integer totalCapacity;
    }
}
