package com.htc.billing.service.app;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.htc.billing.service.app.entities.Employee;
import com.htc.billing.service.app.entities.Products;
import com.htc.billing.service.app.repository.BillingEmployeeRepository;
import com.htc.billing.service.app.repository.BillingProductsRepository;


@Configuration
public class InitialDataLoad  {
	@Bean
	CommandLineRunner commandLineRunner(BillingEmployeeRepository empRepo, BillingProductsRepository prodRepo) {
		return args -> {
			Employee emp1= new Employee(789456, "Stanley","Rodriguez");
			Employee emp2 = new Employee(123456, "Saul","Recinos");
			
			empRepo.saveAll(List.of(emp1,emp2));
			
			Products prod1 = new Products(78548526,"Pollo","Pollo de prueba",2.50,"bolsa",200);
			Products prod2 = new Products(54241565,"Consome","Consome de pollo",0.25,"sobre",500);
			prodRepo.saveAll(List.of(prod1,prod2));
		};

	}
	

}
