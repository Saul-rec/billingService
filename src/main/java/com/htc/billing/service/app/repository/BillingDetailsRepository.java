package com.htc.billing.service.app.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.htc.billing.service.app.entities.BillingDetails;

@Repository
@EnableJpaRepositories
public interface BillingDetailsRepository extends JpaRepository<BillingDetails, Long> {

	public List<BillingDetails> findByBillingCode(long billingCode);
	
	public void deleteAllByBillingCode(long billingCode); 
	
	@Query("SELECT bd FROM BillingDetails bd WHERE bd.billingDetailCode = ?1 and bd.billingCode = ?1 and bd.codProduct = ?1")
	public Optional<BillingDetails> findOneBD(long billingDetailCode, long billingCode, long codeProduct);
	
	
}
