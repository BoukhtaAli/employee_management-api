package ma.java.tutorials.employees.service;

import ma.java.tutorials.employees.dto.UserDTO;

public interface IUserService {

    UserDTO addUser(UserDTO user);

    UserDTO getUserByUsername(String username);
}
