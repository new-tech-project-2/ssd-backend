package dcom.ssdbackend.api.domain.drinker.controller;

import dcom.ssdbackend.api.domain.drinker.dto.DrinkerRequestDto;
import dcom.ssdbackend.api.domain.drinker.dto.DrinkerResponseDto;
import dcom.ssdbackend.api.domain.drinker.service.DrinkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Drinker Controller"})
@RestController
@RequiredArgsConstructor
public class DrinkerController{
   private final DrinkerService drinkerService;

    @ApiOperation("사용자의 술잔 등록")
    @PostMapping ("/auth")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DrinkerResponseDto.DrinkerRegister> drinkerRegister() {
        return ResponseEntity.ok(drinkerService.drinkerRegister());
    }

    @ApiOperation("사용자의 술잔 정보 조회")
    @GetMapping("/drinker")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<DrinkerResponseDto.DrinkerInfo>> drinkerInfo() {
        return ResponseEntity.ok(drinkerService.drinkerInfo());
    }

    @ApiOperation("사용자의 술잔 정보 수정")
    @PostMapping("/drinker/{drinkerId}")
    public ResponseEntity<Void> drinkerModify(@ApiParam(value="사용자 ID", required = true) @PathVariable final String drinkerId, @Validated @RequestBody DrinkerRequestDto.DrinkerModify drinkerModify) {
        drinkerService.drinkerModify(drinkerId,drinkerModify);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("사용자의 술잔 정보 삭제")
    @DeleteMapping("/drinker/{drinkerId}")
    public ResponseEntity<Void> drinkerRemove(@ApiParam(value="사용자 ID", required = true) @PathVariable final String drinkerId) {
        drinkerService.drinkerRemove(drinkerId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
