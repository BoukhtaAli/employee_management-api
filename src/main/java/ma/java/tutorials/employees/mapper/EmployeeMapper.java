package ma.java.tutorials.employees.mapper;

import ma.java.tutorials.employees.domain.Employee;
import ma.java.tutorials.employees.dto.EmployeeDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeDTO employeeDTO);

    EmployeeDTO toDto(Employee employee);

    List<EmployeeDTO> toDtoList(List<Employee> employeeList);
}
