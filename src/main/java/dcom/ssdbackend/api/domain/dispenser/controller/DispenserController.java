package dcom.ssdbackend.api.domain.dispenser.controller;

import dcom.ssdbackend.api.domain.dispenser.dto.DispenserResponseDto;
import dcom.ssdbackend.api.domain.dispenser.service.DispenserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Dispenser Controller"})
@RequestMapping("/dispenser")
@RestController
@RequiredArgsConstructor
public class DispenserController {
    private final DispenserService dispenserService;

    @ApiOperation("사용자 로그인")
    @PostMapping ("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DispenserResponseDto.DrinkerLogin> drinkerLogin() {
        return ResponseEntity.ok(dispenserService.drinkerLogin());
    }

    @ApiOperation("디스펜서 정보 조회")
    @GetMapping("/getdispenser")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DispenserResponseDto.DispenserInfo> getDispenser() {
        return ResponseEntity.ok(dispenserService.getDispenser());
    }
}
