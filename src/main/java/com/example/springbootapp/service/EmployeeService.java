package com.example.springbootapp.service;

import com.example.springbootapp.model.Employee;
import com.example.springbootapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository  employeeRepository;

    public Employee createEmployee(Employee employee) {
        Employee e = new Employee();
        e.setFirstName(employee.getFirstName());
        e.setLastName(employee.getLastName());
        e.setAge(employee.getAge());
        employeeRepository.save(e);
        return e;
    }

    public void removeEmployee(Long id){
        employeeRepository.deleteById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployee(Long id){
        return employeeRepository.findById(id);
    }

    public Employee saveOrUpdate(Employee employee){
        return  employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Employee> pagedResult = employeeRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }
}
