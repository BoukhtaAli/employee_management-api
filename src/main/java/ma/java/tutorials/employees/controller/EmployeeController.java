package ma.java.tutorials.employees.controller;

import ma.java.tutorials.employees.dto.EmployeeDTO;
import ma.java.tutorials.employees.exception.BusinessException;
import ma.java.tutorials.employees.service.IEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employee/")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private IEmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@Valid @RequestBody EmployeeDTO employeeDTO, BindingResult bindingResult) throws BusinessException {

        if(bindingResult.hasErrors()){
            logger.error("Invalid request Body, field : {}, error : {}",bindingResult.getFieldError().getField(), bindingResult.getFieldError().getDefaultMessage());
            throw new BusinessException(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        logger.info("Creating new Employee.");
        return new ResponseEntity<>(employeeService.addNewEmployee(employeeDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO) throws BusinessException {
        logger.info("Updating Existing Employee.");
        return new ResponseEntity<>(employeeService.updateExistingEmployee(employeeDTO), HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("id") Long employeeId){
        logger.info("Retrieving Single Employee.");
        return new ResponseEntity<>(employeeService.retrieveEmployeeById(employeeId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getEmployeeList(Pageable pageable){
        logger.info("Retrieving Employee List.");
        return new ResponseEntity<>(employeeService.retrieveEmployeeList(pageable.getPageNumber(), pageable.getPageSize()), HttpStatus.OK);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id){
        logger.info("Deleting Employee.");
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
