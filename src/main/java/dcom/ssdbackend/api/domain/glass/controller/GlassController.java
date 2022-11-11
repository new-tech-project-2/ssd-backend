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

import java.io.IOException;
import java.util.List;

@Api(tags = {"Glass Controller"})
@RestController
@RequiredArgsConstructor
public class GlassController {
   private final GlassService glassService;

    @ApiOperation("술잔 등록")
    @PostMapping("/glass")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> addGlass(@Validated @RequestBody GlassRequestDto.AddGlass addGlass) throws IOException {
        glassService.addGlass(addGlass);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation("술잔 조회")
    @GetMapping("/glass")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GlassResponseDto.GlassInfo>> getGlass() {
        return ResponseEntity.ok(glassService.getGlass());
    }

    @ApiOperation("술잔 수정")
    @PatchMapping("/glass/{glassId}")
    public ResponseEntity<Void> updateGlass(@ApiParam(value="술잔 ID", required = true) @PathVariable final String glassId, @Validated @RequestBody GlassRequestDto.UpdateGlass updateGlass) throws IOException {
        glassService.updateGlass(glassId,updateGlass);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("술잔 삭제")
    @DeleteMapping("/glass/{glassId}")
    public ResponseEntity<Void> deleteGlass(@ApiParam(value="술잔 ID", required = true) @PathVariable final String glassId) throws IOException {
        glassService.deleteGlass(glassId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ApiOperation("술 마시기")
    @PostMapping("/glass/{glassId}/drink")
    public ResponseEntity<Void> drinkOneGlass(@ApiParam(value="술잔 ID", required = true) @PathVariable final String glassId) throws IOException {
        glassService.drinkOneGlass(glassId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
