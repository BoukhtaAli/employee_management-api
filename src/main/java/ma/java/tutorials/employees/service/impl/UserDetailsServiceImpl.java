package ma.java.tutorials.employees.service.impl;

import ma.java.tutorials.employees.dto.AuthRequestDTO;
import ma.java.tutorials.employees.dto.AuthResponseDTO;
import ma.java.tutorials.employees.dto.UserDTO;
import ma.java.tutorials.employees.exception.BusinessException;
import ma.java.tutorials.employees.service.IUserService;
import ma.java.tutorials.employees.utilis.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    /*
     Load User by username.
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDTO userDTO = userService.getUserByUsername(username);

        if(userDTO != null){

            return new User(userDTO.getUsername(),userDTO.getPassword(),userDTO.getRoles().stream().map(role ->
                    new SimpleGrantedAuthority("ROLE_"+role.getRole())).collect(Collectors.toList()));

        }else {
            throw new UsernameNotFoundException("Invalid Username: "+ username);
        }
    }

    /*
     Authenticate User.
     */

    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) throws BusinessException {

        String username = authRequestDTO.getUsername();
        String password = authRequestDTO.getPassword();

        authenticateUser(username, password);

        final UserDetails userDetails = loadUserByUsername(username);
        final UserDTO user = userService.getUserByUsername(username);
        String accessToken = jwtUtil.generateToken(userDetails);

        return new AuthResponseDTO(user, accessToken);
    }

    private void authenticateUser( String username, String password ) throws BusinessException {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

        } catch( DisabledException disabledException ){
            throw new BusinessException("User is disabled");
        } catch ( BadCredentialsException badCredentialsException ){
            throw new BusinessException("Bad Credentials");
        }
    }

    /*
     Refresh Token.
     */

    public String refreshToken(String header) throws BusinessException {

        String refreshToken;

        String jwtToken;
        String username;

        if(header != null && header.startsWith("Bearer ")){

            jwtToken = header.substring(7);

            try {
                username = jwtUtil.getUsernameFromToken(jwtToken);
            } catch (Exception exception){
                logger.error(exception.getMessage());
                throw new BusinessException("Could Not Get Username from Token");
            }

            if(username !=null) {

                UserDetails userDetails = this.loadUserByUsername(username);

                if(jwtUtil.validateToken(jwtToken, userDetails)){

                    refreshToken = jwtUtil.refreshToken(userDetails);

                } else {
                    logger.error("Invalid or Expired Token");
                    throw new BusinessException("Invalid or Expired Token");
                }

            }else {
                logger.error("Invalid Username");
                throw new BusinessException("Invalid Username");
            }

        } else {
            logger.error("Header does not contain Bearer Token");
            throw new BusinessException("Header does not contain Bearer Token");
        }

        return refreshToken;
    }
}
