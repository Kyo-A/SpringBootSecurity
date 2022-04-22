package com.example.springbootapp.aop;

import com.example.springbootapp.model.Employee;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmployeeServiceAspect {

    @Before("execution(public * *..EmployeeService.*(..)) && args(employee)")
    public void beforeAdvice(JoinPoint joinPoint, Employee employee){
        System.out.println("Before method: " + joinPoint.getSignature());
        System.out.println("Creating/Updating Employee with Employee: " + employee);
    }

    @After("execution(public * *..EmployeeService.*(..)) && args(employee)")
    public void afterAdvice(JoinPoint joinPoint, Employee employee){
        System.out.println("After method: " + joinPoint.getSignature());
        System.out.println("Successfully created/updated Employee with Employee: " + employee);
    }

    @Before("execution(public void *..EmployeeService.*(..)) && args(id)")
    public void beforeAdvice(JoinPoint joinPoint, Long id){
        System.out.println("Before method: " + joinPoint.getSignature());
        System.out.println("Deleting Employee with id: " + id);
    }

    @After("execution(public void *..EmployeeService.*(..)) && args(id)")
    public void afterAdvice(JoinPoint joinPoint, Long id){
        System.out.println("After method: " + joinPoint.getSignature());
        System.out.println("Successfully deleted Employee with id: " + id);
    }

}
