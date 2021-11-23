package ma.java.tutorials.employees.config;

import ma.java.tutorials.employees.dto.UserDTO;
import ma.java.tutorials.employees.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsConfiguration implements UserDetailsService {

    @Autowired
    private IUserService userService;

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
}
