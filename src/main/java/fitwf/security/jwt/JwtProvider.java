package fitwf.security.jwt;

import fitwf.model.UserPrinciple;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {
    @Value("${jwt.token.secret}")
    private String jwtSecret;

    public String generateJwtToken(Authentication authentication) {
        System.out.println("generateJwtToken");
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return "Bearer_" + Jwts.builder()
                .setSubject(userPrinciple.getUsername())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        System.out.println("getUsernameFromJwtToken");
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        System.out.println("validateJwtToken");
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        return true;
    }
}













