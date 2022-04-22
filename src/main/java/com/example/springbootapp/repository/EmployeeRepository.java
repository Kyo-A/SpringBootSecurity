package com.example.springbootapp.repository;

import com.example.springbootapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
