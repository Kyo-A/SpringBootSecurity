package com.example.springbootapp.controller;

import com.example.springbootapp.model.Employee;
import com.example.springbootapp.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    EmployeeService employeeServiceMock;

    Employee e1 = new Employee(1L, "John", "Wick", 36);
    Employee e2 = new Employee(2L, "Elon", "Musk", 36);
    Employee e3 = new Employee(3L, "Jean", "Dupont", 36);

    @Test
    public void getAllEmployees_Success() throws Exception{
        List<Employee> employees = new ArrayList<>(Arrays.asList(e1, e2, e3));

        Mockito.when(employeeServiceMock.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(MockMvcRequestBuilders.get("/all/employee")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[*].lastName").isNotEmpty());
    }

    @Test
    public void getEmployeeById_Success() throws Exception{
        Mockito.when(employeeServiceMock.getEmployee(e1.getId())).thenReturn(Optional.of(e1));

        mockMvc.perform(MockMvcRequestBuilders.get("/1/employee")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.lastName", is("Wick")));
    }

    @Test
    public void createEmployee_Success() throws Exception{
        Employee e = new Employee(4L, "Bob", "Marley", 60);

        Mockito.when(employeeServiceMock.saveOrUpdate(e)).thenReturn(e);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/add/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(e));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.lastName", is("Marley")));
    }

    @Test
    public void deleteEmployeeById_Success() throws Exception{

        Mockito.when(employeeServiceMock.getEmployee(e1.getId())).thenReturn(Optional.of(e1));

        mockMvc.perform(MockMvcRequestBuilders.delete("/delete/employee/1"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void editEmploye_success() throws Exception {
        Employee e = new Employee(1L, "John", "Travolta", 56);

        Mockito.when(employeeServiceMock.getEmployee(e1.getId())).thenReturn(Optional.of(e1));
        Mockito.when(employeeServiceMock.saveOrUpdate(e)).thenReturn(e);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/edit/employe/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(e));

        mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("John")));
    }



}
