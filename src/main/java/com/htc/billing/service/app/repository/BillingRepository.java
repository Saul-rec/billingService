package com.htc.billing.service.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.htc.billing.service.app.entities.Billing;

@Repository
@EnableJpaRepositories
public interface BillingRepository extends JpaRepository<Billing, Long>{


	public void deleteByBillingCode(long billingCode);
}
