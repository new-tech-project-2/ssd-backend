package dcom.ssdbackend.api.domain.glass.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

public class GlassRequestDto {
    @ApiModel(value="술잔 등록")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddGlass {
        private String glassId;
        private String dispenserId;
    }

    @ApiModel(value="술잔 수정")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateGlass {
        private String drinkerName;
        private String detail;
        private Integer totalCapacity;
    }
}
