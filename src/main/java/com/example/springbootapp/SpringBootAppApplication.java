package com.example.springbootapp;

import com.example.springbootapp.audit.AuditorAwareImpl;
import com.example.springbootapp.model.Employee;
import com.example.springbootapp.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SpringBootAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAppApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    @Bean
	public CommandLineRunner demo(EmployeeRepository employeRepository) {
		return (args) ->{
			employeRepository.save(new Employee(1L , "NOM1", "PRENOM1", 45));
		    employeRepository.save(new Employee(2L, "NOM2", "PRENOM2", 43));
			employeRepository.save(new Employee(3L,"NOM3", "PRENOM3", 23));
		};
	}

}
