package com.htc.billing.service.app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.htc.billing.service.app.entities.BillingDetails;

@Repository
@EnableJpaRepositories
public interface BillingDetailsRepository extends JpaRepository<BillingDetails, Long> {

	public List<BillingDetails> findByBillingCode(long billingCode);
	
	public void deleteAllByBillingCode(long billingCode); 
	
	
}
