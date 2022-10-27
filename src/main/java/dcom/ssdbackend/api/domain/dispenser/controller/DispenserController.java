package dcom.ssdbackend.api.domain.dispenser.controller;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import dcom.ssdbackend.api.domain.dispenser.dto.DispenserResponseDto;
import dcom.ssdbackend.api.domain.dispenser.repository.DispenserRepository;
import dcom.ssdbackend.api.domain.dispenser.service.DispenserService;
import dcom.ssdbackend.api.global.jwt.JwtProvider;
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
    private final DispenserRepository dispenserRepository;
    private final DispenserService dispenserService;
    private final JwtProvider jwtProvider;

    @ApiOperation("사용자 로그인")
    @PostMapping ("/login")
    public String drinkerLogin() {
        Dispenser dispenser = dispenserRepository.findById(jwtProvider.getDispenserTokenInHeader()).get();
        return jwtProvider.generateDrinkerToken(dispenser.getId());
    }

    @ApiOperation("디스펜서 정보 조회")
    @GetMapping("/getdispenser")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DispenserResponseDto.DispenserInfo> getDispenser() {
        return ResponseEntity.ok(dispenserService.getDispenser());
    }
}
