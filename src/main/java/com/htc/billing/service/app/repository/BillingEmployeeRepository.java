package com.htc.billing.service.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.htc.billing.service.app.entities.Employee;

@Repository
@EnableJpaRepositories
public interface BillingEmployeeRepository extends JpaRepository<Employee, Integer>{

	//@Query(value = "select e.cod_employee from com.htc.billing.service.app.entities.Employee e where e.cod_employee = ?1")
	public Optional<Employee> findByCodeEmployee(long codeEmployee);
	
}
