package ma.java.tutorials.employees.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.java.tutorials.employees.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter @Setter @NoArgsConstructor
public class InternalUserDetails implements org.springframework.security.core.userdetails.UserDetails, Serializable {

    private UserDTO user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(role ->
            new SimpleGrantedAuthority("ROLE_"+role.getRole())).collect(Collectors.toList()) ;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
