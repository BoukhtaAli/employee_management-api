package ma.java.tutorials.employees.utilis;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import ma.java.tutorials.employees.dto.RoleDTO;
import ma.java.tutorials.employees.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final String algorithmStr = "boukhta-ali-employee-management-secret";

    public String generateToken(UserDTO user, String url){

        Algorithm algorithm = Algorithm.HMAC256(algorithmStr.getBytes());

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ 1000 * 60 * 3))
                .withIssuer(url)
                .withClaim("roles",user.getRoles().stream().map(roleDTO -> "ROLE_".concat(roleDTO.getRole())).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String refreshToken(UserDTO user, String url){

        Algorithm algorithm = Algorithm.HMAC256(algorithmStr.getBytes());

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+1000 * 60 * 10))
                .withIssuer(url)
                .sign(algorithm);
    }
}
