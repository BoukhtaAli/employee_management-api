package ma.java.tutorials.employees.service.impl;

import ma.java.tutorials.employees.domain.User;
import ma.java.tutorials.employees.dto.RoleDTO;
import ma.java.tutorials.employees.dto.UserDTO;
import ma.java.tutorials.employees.mapper.UserMapper;
import ma.java.tutorials.employees.repository.UserRepository;
import ma.java.tutorials.employees.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO addUser(UserDTO userDTO) {

        Set<RoleDTO> roles  = new HashSet<>();
        roles.add(new RoleDTO("USER"));

        userDTO.setRoles(roles);
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User user = userMapper.toUserEntity(userDTO);

        return userMapper.toUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUserByUsername(String username) {

        Optional<User> user = userRepository.findByUsername(username);

        return user.map(value -> userMapper.toUserDTO(value)).orElse(null);
    }
}
