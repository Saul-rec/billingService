package com.htc.billing.service.app.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.htc.billing.service.app.exceptions.ServiceFaultException;
import com.htc.billing.service.app.repository.BillingDetailsRepository;
import com.htc.billing.service.app.repository.BillingRepository;
import com.htc.billing.service.generated.ServiceStatus;

@Service

public class DeleteBillingService {
	
	private BillingDetailsRepository detailsRepo;
	private BillingRepository billingRepo;
	private ServiceStatus serviceStatus = new ServiceStatus();
	
	
	public DeleteBillingService(BillingDetailsRepository detailsRepo, BillingRepository billingRepo) {
		super();
		this.detailsRepo = detailsRepo;
		this.billingRepo = billingRepo;
	}
	
	@Transactional
	public ServiceStatus deleteBilling(long billingCode) {
		serviceStatus.getServiceResult().clear();
		if (!billingRepo.findById(billingCode).isPresent()) {
			serviceStatus.setServiceCode("404: NOT FOUND");
			serviceStatus.getServiceResult().add("billing with code " + billingCode + 
											" not found. Operation Cancelled");
			throw new ServiceFaultException("DATA ERROR", serviceStatus);
		}
		try {
			detailsRepo.deleteAllByBillingCode(billingCode);
			billingRepo.deleteByBillingCode(billingCode);
		} catch (Exception e) {
			serviceStatus.setServiceCode("500: SERVER ERROR");
			serviceStatus.getServiceResult().add(e.toString());
			throw new ServiceFaultException("DATA ERROR", serviceStatus);
		}
		
		serviceStatus.setServiceCode("200: DELETED");
		serviceStatus.getServiceResult().add("billing with code " + billingCode + " deleted successfully");
	
		return serviceStatus;
		
	}
	

}
