package ma.java.tutorials.employees.controller;

import ma.java.tutorials.employees.dto.AuthRequestDTO;
import ma.java.tutorials.employees.dto.AuthResponseDTO;
import ma.java.tutorials.employees.dto.UserDTO;
import ma.java.tutorials.employees.exception.BusinessException;
import ma.java.tutorials.employees.service.IUserService;
import ma.java.tutorials.employees.service.impl.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login( @RequestBody AuthRequestDTO authentication ) throws BusinessException {

        logger.info("Login Attempt By User: {}.", authentication.getUsername());

        AuthResponseDTO response = userDetailsService.authenticate(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-ACCESS-TOKEN",response.getAccessToken());

        return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register( @RequestBody UserDTO user ){

        logger.info("Register Attempt for User: {}.", user.getUsername());

        return ResponseEntity.ok(userService.addUser(user));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<String> refreshToken ( @RequestHeader("AUTHORIZATION") String header ) throws BusinessException {

        logger.info("Refresh Token Attempt.");

        String refreshToken = userDetailsService.refreshToken(header);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-REFRESH-TOKEN",refreshToken);

        return new ResponseEntity(refreshToken, httpHeaders, HttpStatus.OK);
    }
}
