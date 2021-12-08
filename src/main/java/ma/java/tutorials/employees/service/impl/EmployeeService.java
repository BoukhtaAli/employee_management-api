package ma.java.tutorials.employees.service.impl;

import ma.java.tutorials.employees.domain.Employee;
import ma.java.tutorials.employees.dto.EmployeeDTO;
import ma.java.tutorials.employees.exception.BusinessException;
import ma.java.tutorials.employees.mapper.EmployeeMapper;
import ma.java.tutorials.employees.repository.EmployeeRepository;
import ma.java.tutorials.employees.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO addNewEmployee(EmployeeDTO employeeDTO) {

        Employee employee = employeeMapper.toEntity(employeeDTO);

        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDTO updateExistingEmployee(EmployeeDTO employeeDTO) throws BusinessException {

        if(!employeeRepository.existsById(employeeDTO.getId())){
            throw new BusinessException("Employee Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Employee employee = employeeMapper.toEntity(employeeDTO);

        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDTO retrieveEmployeeById(Long id) {

        Optional<Employee> employee = employeeRepository.findById(id);

        return employee.map(value -> employeeMapper.toDto(value)).orElse(null);

    }

    @Override
    public List<EmployeeDTO> retrieveEmployeeList(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page,size);

        return employeeMapper.toDtoList(employeeRepository.findAllByOrderByIdDesc(pageable).toList());
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
}
