package project.purelabel.global.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.purelabel.global.exception.InvalidTokenException;

import java.security.PublicKey;
import java.util.Base64;
import java.util.Map;

import static project.purelabel.global.exception.errorcode.InvalidExceptionErrorCode.*;

@Component
public class JwtParser {

    @Value("${secret.jwt-secret-key}")
    private String secretKey;

    public Long getUserPkFromToken(String accessToken) {
        try {
            return Long.parseLong(Jwts.parser()
                    .setSigningKey(secretKey)
                            .build()
                    .parseClaimsJws(accessToken)
                    .getBody().getSubject());
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException(EXPIRED_TOKEN);
        } catch (UnsupportedJwtException | SignatureException | MalformedJwtException e){
            throw new InvalidTokenException(MALFORMED_TOKEN, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    public Claims parsePublicKeyAndGetClaims(String idToken, PublicKey publicKey) {
        try {
            return Jwts.parser()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(idToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException(EXPIRED_TOKEN);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e){
            throw new InvalidTokenException(MALFORMED_TOKEN, e.getMessage());
        }
    }
}

