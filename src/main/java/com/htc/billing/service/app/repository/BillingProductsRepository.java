package com.htc.billing.service.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.htc.billing.service.app.entities.Products;

@Repository
@EnableJpaRepositories
public interface BillingProductsRepository extends CrudRepository<Products, Integer>{

	//@Query(value = "select p.codProduct from com.htc.billing.service.app.entities.Products p where p.codProduct = ?1")
	Optional<Products> findByCodeProduct(long codeProduct);
	
}
