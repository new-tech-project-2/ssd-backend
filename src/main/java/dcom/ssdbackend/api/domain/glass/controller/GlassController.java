package dcom.ssdbackend.api.domain.glass.controller;

import dcom.ssdbackend.api.domain.glass.dto.GlassRequestDto;
import dcom.ssdbackend.api.domain.glass.dto.GlassResponseDto;
import dcom.ssdbackend.api.domain.glass.service.GlassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Drinker Controller"})
@RestController
@RequiredArgsConstructor
public class GlassController {
   private final GlassService glassService;

    @ApiOperation("사용자의 술잔 등록")
    @PostMapping ("/auth")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GlassResponseDto.GlassRegister> glassRegister() {
        return ResponseEntity.ok(glassService.glassRegister());
    }

    @ApiOperation("사용자의 술잔 정보 조회")
    @GetMapping("/glass")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GlassResponseDto.GlassInfo>> glassInfo() {
        return ResponseEntity.ok(glassService.glassInfo());
    }

    @ApiOperation("술잔 정보 수정")
    @PostMapping("/glass/{glassId}")
    public ResponseEntity<Void> glassModify(@ApiParam(value="술잔 ID", required = true) @PathVariable final String glassId, @Validated @RequestBody GlassRequestDto.GlassModify glassModify) {
        glassService.glassModify(glassId,glassModify);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("술잔 정보 삭제")
    @DeleteMapping("/glass/{glassId}")
    public ResponseEntity<Void> glassRemove(@ApiParam(value="술잔 ID", required = true) @PathVariable final String glassId) {
        glassService.glassRemove(glassId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
