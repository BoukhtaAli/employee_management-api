package ma.java.tutorials.employees.mapper;

import ma.java.tutorials.employees.domain.Role;
import ma.java.tutorials.employees.domain.User;
import ma.java.tutorials.employees.dto.RoleDTO;
import ma.java.tutorials.employees.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Role toRoleEntity(RoleDTO roleDTO);

    RoleDTO toRoleDTO(Role role);

    Set<RoleDTO> toRoleDTOSet(Set<Role> roles);

    Set<Role> toRoleEntitySet(Set<RoleDTO> roleDTOSet);

    User toUserEntity(UserDTO userDTO);

    UserDTO toUserDTO(User user);
}
