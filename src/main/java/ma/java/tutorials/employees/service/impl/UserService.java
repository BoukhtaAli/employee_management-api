package ma.java.tutorials.employees.service.impl;

import ma.java.tutorials.employees.domain.User;
import ma.java.tutorials.employees.dto.UserDTO;
import ma.java.tutorials.employees.mapper.UserMapper;
import ma.java.tutorials.employees.repository.UserRepository;
import ma.java.tutorials.employees.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO addUser(UserDTO userDTO) {

        User user = userMapper.toUserEntity(userDTO);

        return userMapper.toUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUserByUsername(String username) {

        Optional<User> user = userRepository.findByUsername(username);

        return user.map(value -> userMapper.toUserDTO(value)).orElse(null);
    }
}
