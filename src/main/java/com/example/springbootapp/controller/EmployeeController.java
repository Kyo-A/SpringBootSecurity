package com.example.springbootapp.controller;

import com.example.springbootapp.dto.EmployeeDto;
import com.example.springbootapp.model.Employee;
import com.example.springbootapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // http://localhost:8080/add/employee?firstName=Wick&lastName=John&age=23
    //@RequestMapping(value = "/add/employee", method = RequestMethod.GET)
    //public Employee addEmployee(@RequestParam("firstName") String firstName,
    //@RequestParam("lastName") String lastName,
    //@RequestParam("age") int age){
    //Employee e = new Employee(firstName, lastName, age);
    //return  employeeService.createEmployee(e);
    // }

    // http://localhost:8080/delete/employee/1
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @RequestMapping(value = "/delete/employee/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteEmployee2(@PathVariable("id") Long id){
        Employee employeeToDelete = employeeService.getEmployee(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found :: " + id));
        employeeService.removeEmployee(employeeToDelete.getId());
        return new ResponseEntity<String>("Employe Deleted", HttpStatus.ACCEPTED);
    }

    // http://localhost:8080/all/employee
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @RequestMapping(value = "/all/employee", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> fetchAllEmployees(){
        return new ResponseEntity<List<Employee>>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    // http://localhost:8080/1/employee
    @RequestMapping(value = "/{id}/employee", method = RequestMethod.GET)
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id){
        Employee employee = employeeService.getEmployee(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found :: " + id));
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    // http://localhost:8080/1/employee
    @RequestMapping(value = "/{id}/employee", method = RequestMethod.PUT)
    public ResponseEntity<Employee> editEmployee(@PathVariable("id") Long id,
                                                 @Valid @RequestBody EmployeeDto employeeDto){
        Employee employeeToUpdate = employeeService.getEmployee(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found :: " + id));
        employeeToUpdate.setFirstName(employeeDto.getFirstName());
        employeeToUpdate.setLastName(employeeDto.getLastName());
        employeeService.saveOrUpdate(employeeToUpdate);
        return new ResponseEntity<Employee>(employeeToUpdate, HttpStatus.OK);
    }

    // http://localhost:8080/add/employee
    @RequestMapping(value = "/add/employee", method = RequestMethod.POST)
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody EmployeeDto employeeDto){
        Employee e = new Employee();
        e.setFirstName(employeeDto.getFirstName());
        e.setLastName(employeeDto.getLastName());
        employeeService.saveOrUpdate(e);
        return new ResponseEntity<Employee>(e, HttpStatus.CREATED);
    }

    // http://localhost:8080/all/employees?pageSize=5
    // http://localhost:8080/all/employees?pageSize=5&pageNo=1
    // http://localhost:8080/all/employees?pageSize=5&pageNo=2
    // http://localhost:8080/all/employees?pageSize=5&pageNo=1&sortBy=id
    @RequestMapping(value = "/all/employees", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {
        List<Employee> list = employeeService.getAllEmployees(pageNo, pageSize, sortBy);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }
}
