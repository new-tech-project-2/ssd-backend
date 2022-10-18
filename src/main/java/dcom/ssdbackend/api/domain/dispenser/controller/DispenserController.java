package dcom.ssdbackend.api.domain.dispenser.controller;

import dcom.ssdbackend.api.domain.dispenser.dto.DispenserResponseDto;
import dcom.ssdbackend.api.domain.dispenser.service.DispenserService;
import dcom.ssdbackend.api.domain.drinker.dto.DrinkerResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Dispenser Controller"})
@RestController
@RequiredArgsConstructor
public class DispenserController {

    private final DispenserService dispenserService;

    @ApiOperation("디스펜서의 정보 조회")
    @GetMapping("/dispenser")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DispenserResponseDto.DispenserInfo> dispenserInfo() {
        return ResponseEntity.ok(dispenserService.dispenserInfo());
    }

    @ApiOperation("디스펜서 시작")
    @PostMapping("/dispenser")
    public ResponseEntity<Void> dispenserStart() {
        dispenserService.dispenserStart();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ApiOperation("디스펜서 중지")
    @DeleteMapping("/dispenser")
    public ResponseEntity<Void> dispenserStop() {
        dispenserService.dispenserStop();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
