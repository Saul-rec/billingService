package com.htc.billing.service.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.htc.billing.service.app.entities.Billing;
import com.htc.billing.service.app.entities.Employee;
import com.htc.billing.service.app.entities.Products;
import com.htc.billing.service.app.exceptions.ServiceFaultException;
import com.htc.billing.service.app.repository.BillingEmployeeRepository;
import com.htc.billing.service.app.repository.BillingProductsRepository;
import com.htc.billing.service.app.repository.BillingRepository;
import com.htc.billing.service.generated.NewBillingRequest;
import com.htc.billing.service.generated.ServiceStatus;
import com.htc.billing.service.generated.UpdateBillingRequest;

@Component
public class ValidationService {

	@Autowired
	private BillingEmployeeRepository billingEmployeeRepo;
	@Autowired
	private BillingProductsRepository billingProductsRepo;
	@Autowired
	private BillingRepository billingRepo;
	
	
	private ServiceStatus serviceStatus = new ServiceStatus();
	private static final String EXCEPTION = "Data Validation Failed. See Reasons";
	private static final String NOT_FOUND = "404: NOT FOUND";
	
	public void checkEmployeeExistence(long codEmployee) {

		Optional<Employee> anEmployee = billingEmployeeRepo.findByCodeEmployee(codEmployee);
		if (anEmployee.isEmpty()) {
			serviceStatus.setServiceCode(NOT_FOUND);
			serviceStatus.getServiceResult().add("The employee " + codEmployee + " was not found");
			throw new ServiceFaultException(EXCEPTION, serviceStatus);
		}
	}

	public void noEmptyFieldInListValidation(NewBillingRequest request) {
		for (int i = 0; i < request.getProductDetails().size(); i++) {
			long codProdu = request.getProductDetails().get(i).getCodProduct();
			//List<long> allProductCodes = new ArrayList<>();
			//validar que no haya dos veces el mismo prod
			int quantity = request.getProductDetails().get(i).getQuantity();
			if (codProdu == 0 || quantity == 0) {
				serviceStatus.setServiceCode("500: BAD REQUEST");
				serviceStatus.getServiceResult().add("No empty fields allowed in products details");
				throw new ServiceFaultException(EXCEPTION, serviceStatus);
			}
		}
	}

	public void allProductsInCreateRequestExist(NewBillingRequest request) {
		for (int i = 0; i < request.getProductDetails().size(); i++) {
			long codProduct = request.getProductDetails().get(i).getCodProduct();
			Optional<Products> aProduct = billingProductsRepo.findByCodeProduct(codProduct);
			if (aProduct.isEmpty()) {
				serviceStatus.setServiceCode(NOT_FOUND);
				serviceStatus.getServiceResult()
						.add("product " + codProduct + " was not found in DB." + "Cannot Proceed");
				throw new ServiceFaultException("DATA ERROR", serviceStatus);
			}
		}
	}

	public void checkBillingExistence(long billingCode) {
		Optional<Billing> aBill = billingRepo.findById(billingCode);
		if (!aBill.isPresent()) {
			serviceStatus.setServiceCode(NOT_FOUND);
			serviceStatus.getServiceResult().add("billing with code " + billingCode + " was not found");
			throw new ServiceFaultException("DATA ERROR", serviceStatus);
		} 
	}
	
	public void noEmptyFieldsForUpdate(UpdateBillingRequest request) {
		for (int i = 0; i < request.getProductDetails().size(); i++) {
			long codProdu = request.getProductDetails().get(i).getCodProduct();
			int quatity = request.getProductDetails().get(i).getQuantity();
			if (codProdu == 0 || quatity == 0 ) {
				serviceStatus.setServiceCode("500: BAD REQUEST");
				serviceStatus.getServiceResult().add("No empty fields allowed in products details section");
				throw new ServiceFaultException(EXCEPTION, serviceStatus);
			}
		}
	}
	
	public void allProductsInUpdateRequestExist(UpdateBillingRequest request) {
		for (int i = 0; i < request.getProductDetails().size(); i++) {
			long codProduct = request.getProductDetails().get(i).getCodProduct();
			Optional<Products> aProduct = billingProductsRepo.findByCodeProduct(codProduct);
			if (aProduct.isEmpty()) {
				serviceStatus.setServiceCode(NOT_FOUND);
				serviceStatus.getServiceResult()
						.add("product code " + codProduct + " was not found in DB." + "Cannot Proceed");
				throw new ServiceFaultException("DATA ERROR", serviceStatus);
			}
		}
	}

}
