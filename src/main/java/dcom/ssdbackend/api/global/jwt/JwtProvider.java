package dcom.ssdbackend.api.global.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final HttpServletRequest request;

    String ssdSecretKey="ssdSecretKey";
    byte[] decodedSsdSecretKey = Base64.getDecoder().decode(ssdSecretKey);

    public String generateDrinkerToken(String dispenserToken) {
        Date now = new Date();

        var claims = Jwts.claims();
        claims.put("drinkerId",UUID.randomUUID().toString());
        claims.put("dispenserToken", dispenserToken);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(180).toMillis()))
                .signWith(SignatureAlgorithm.HS256,decodedSsdSecretKey)
                .compact();
    }

    public boolean verifyDrinkerToken(String token){
        try{
            Jwts.parser().setSigningKey(decodedSsdSecretKey).parseClaimsJws(token);
            return true;
        }catch(ExpiredJwtException exception){
            return false;
        }catch (JwtException exception){
            return false;
        }catch (Exception e){
            return false;
        }
    }

    public String getDrinkerTokenInHeader(){
        String drinkerToken = request.getHeader("drinkerToken");

        if(drinkerToken==null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized Request");
        return drinkerToken;
    }

    public String getDispenserTokenInHeader(){
        String dispenserToken = request.getHeader("dispenserToken");

        if(dispenserToken==null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized Request");
        return dispenserToken;
    }

    public String getDrinkerId(String token){
        String drinkerId=(String) Jwts.parser().setSigningKey(decodedSsdSecretKey).parseClaimsJws(token).getBody().get("drinkerId");
        return drinkerId;
    }

    public String getDispenserToken(String token){
        String dispenserToken=(String) Jwts.parser().setSigningKey(decodedSsdSecretKey).parseClaimsJws(token).getBody().get("dispenserToken");
        return dispenserToken;
    }
}
