package ma.java.tutorials.employees.utilis;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    /*
     Secret Key.
     */

    private final String secretKey = "boukhta-ali-employee-management-secret";

    /*
     Get Username from Token.
     */

    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    /*
     Validate Token.
     */

    public boolean validateToken(String token, UserDetails userDetails){
        final String username = this.getUsernameFromToken(token);
        return ( username.equals(userDetails.getUsername()) && !this.isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        final Date expiryDate = getExpirationDateFromToken(token);
        return expiryDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /*
     Create JWT.
     */

    public String generateToken(UserDetails userDetails){

        Map<String, Object> claims = new HashMap<>(0);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(SignatureAlgorithm.HS512 , secretKey).compact();
    }

    /*
     Refresh JWT.
     */

    public String refreshToken( UserDetails userDetails ){

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(SignatureAlgorithm.HS512 , secretKey).compact();
    }
}
