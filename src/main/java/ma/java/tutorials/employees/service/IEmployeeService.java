package ma.java.tutorials.employees.service;

import ma.java.tutorials.employees.dto.EmployeeDTO;
import ma.java.tutorials.employees.exception.BusinessException;

import java.util.List;

public interface IEmployeeService {

    EmployeeDTO addNewEmployee(EmployeeDTO employee);

    EmployeeDTO updateExistingEmployee(EmployeeDTO employee) throws BusinessException;

    EmployeeDTO retrieveEmployeeById(Long id);

    List<EmployeeDTO> retrieveEmployeeList(Integer page, Integer size);

    void deleteEmployeeById(Long id);
}
