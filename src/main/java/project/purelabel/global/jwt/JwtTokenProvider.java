package project.purelabel.global.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final long REFRESH_TOKEN_EXPIRED_IN = 15L * 24 * 60 * 60 * 1000; // 15일
    private final String secretKey;
    private final JwtParser jwtParser;
    private final long ACCESS_TOKEN_EXPIRED_IN;

    public JwtTokenProvider(@Value("${secret.jwt-secret-key}") String secretKey,
                            @Value("${spring.accesstoken.expired-date}") Long accesstokenExpiredDate) {
        this.secretKey = secretKey;
        this.jwtParser = Jwts.parser().setSigningKey(secretKey).build();
        this.ACCESS_TOKEN_EXPIRED_IN = accesstokenExpiredDate;
    }

    public String createAccessToken(Long pk) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRED_IN);

        return Jwts.builder()
                .setSubject(String.valueOf(pk))
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(Long pk) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_TOKEN_EXPIRED_IN);

        return Jwts.builder()
                .setSubject(String.valueOf(pk))
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
