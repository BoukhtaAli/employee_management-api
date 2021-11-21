package ma.java.tutorials.employees.config;

import ma.java.tutorials.employees.domain.InternalUserDetails;
import ma.java.tutorials.employees.dto.UserDTO;
import ma.java.tutorials.employees.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsConfiguration implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDTO userDTO = userService.getUserByUsername(username);

        InternalUserDetails internalUserDetails;

        if(userDTO != null){
            internalUserDetails = new InternalUserDetails();
            internalUserDetails.setUser(userDTO);
            return  internalUserDetails;
        }else {
            throw new UsernameNotFoundException("Invalid Username: "+ username);
        }
    }
}
