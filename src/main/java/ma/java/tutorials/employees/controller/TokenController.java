package ma.java.tutorials.employees.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.java.tutorials.employees.dto.UserDTO;
import ma.java.tutorials.employees.service.IUserService;
import ma.java.tutorials.employees.utilis.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/token/refresh")
public class TokenController {

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorizationHeader!= null && authorizationHeader.contains("Bearer ")){

            try {

                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("boukhta-ali-employee-management-secret".getBytes());

                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);

                String username = decodedJWT.getSubject();

                UserDTO user = userService.getUserByUsername(username);

                String accessToken = jwtUtil.generateToken(user, request.getRequestURI());
                refreshToken = jwtUtil.refreshToken(user, request.getRequestURI());

                Map<String, String> tokens = new HashMap<>(0);
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);

            }catch (Exception exception){

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                Map<String, String> errors = new HashMap<>(0);
                errors.put("error_message", exception.getMessage());

                new ObjectMapper().writeValue(response.getOutputStream(),errors);
            }
        }
    }
}
