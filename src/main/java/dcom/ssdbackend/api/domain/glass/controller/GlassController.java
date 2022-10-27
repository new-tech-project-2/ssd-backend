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

@Api(tags = {"Glass Controller"})
@RequestMapping("/glass")
@RestController
@RequiredArgsConstructor
public class GlassController {
   private final GlassService glassService;

    @ApiOperation("술잔 정보 조회")
    @GetMapping("/getglass")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GlassResponseDto.GlassInfo>> getGlass() {
        return ResponseEntity.ok(glassService.getGlass());
    }

    @ApiOperation("술잔 정보 수정")
    @PatchMapping("/updateglass/{glassId}")
    public ResponseEntity<Void> updateGlass(@ApiParam(value="술잔 ID", required = true) @PathVariable final String glassId, @Validated @RequestBody GlassRequestDto.UpdateGlass updateGlass) {
        glassService.updateGlass(glassId,updateGlass);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("술잔 정보 삭제")
    @DeleteMapping("/deleteglass/{glassId}")
    public ResponseEntity<Void> deleteGlass(@ApiParam(value="술잔 ID", required = true) @PathVariable final String glassId) {
        glassService.deleteGlass(glassId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
