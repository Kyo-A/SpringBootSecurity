package com.example.springbootapp.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    @NotEmpty
    @Size(min = 2, message = "first name should have at least 2 characters")
    private String firstName;
    @NotEmpty
    @Size(min = 2, message = "last name should have at least 2 characters")
    private String lastName;
}
