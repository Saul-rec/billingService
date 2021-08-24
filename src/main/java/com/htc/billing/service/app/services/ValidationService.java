package com.htc.billing.service.app.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class ValidationService {
	private BillingEmployeeRepository billingEmployeeRepo;
	private BillingProductsRepository billingProductsRepo;
	@Autowired
	private BillingRepository billingRepo;

	private ServiceStatus serviceStatus = new ServiceStatus();
	private static final String EXCEPTION = "Unsatisfied data validation.";
	private static final String NOT_FOUND = "404: NOT FOUND";
	private static final String BAD_REQUEST = "500: BAD REQUEST";

	@Autowired
	public ValidationService(BillingEmployeeRepository billingEmployeeRepo,
			BillingProductsRepository billingProductsRepo) {
		this.billingEmployeeRepo = billingEmployeeRepo;
		this.billingProductsRepo = billingProductsRepo;
	}

	public void checkEmployeeExistence(long codEmployee) {
		serviceStatus.getServiceResult().clear();
		Optional<Employee> anEmployee = billingEmployeeRepo.findByCodeEmployee(codEmployee);
		if (anEmployee.isEmpty()) {
			serviceStatus.setServiceCode(NOT_FOUND);
			serviceStatus.getServiceResult().add("The employee " + codEmployee + " was not found");
			throw new ServiceFaultException(EXCEPTION, serviceStatus);
		}
	}

	public void createFieldsValidation(NewBillingRequest request) {
		serviceStatus.getServiceResult().clear();
		ArrayList<Long> allProductCodes = new ArrayList<>();

		for (int i = 0; i < request.getProductDetails().size(); i++) {
			long codProduct = request.getProductDetails().get(i).getCodProduct();
			int quantity = request.getProductDetails().get(i).getQuantity();
			allProductCodes.add(codProduct);
			if (codProduct == 0 || quantity == 0) {
				serviceStatus.setServiceCode(BAD_REQUEST);
				serviceStatus.getServiceResult().add("No empty fields allowed in products details");
				throw new ServiceFaultException(EXCEPTION, serviceStatus);
			}
			Optional<Products> aProduct = billingProductsRepo.findByCodeProduct(codProduct);
			if (aProduct.isEmpty()) {
				serviceStatus.setServiceCode(NOT_FOUND);
				serviceStatus.getServiceResult()
						.add("product " + codProduct + " was not found in DB." + "Cannot Proceed");
				throw new ServiceFaultException(EXCEPTION, serviceStatus);
			}
		}
		for (int j = 0; j < allProductCodes.size(); j++) {
			for (int k = j + 1; k < allProductCodes.size(); k++) {
				if (allProductCodes.get(j).equals(allProductCodes.get(k))) {
					serviceStatus.setServiceCode(BAD_REQUEST);
					serviceStatus.getServiceResult().add("Same code product twice or more times.");
					throw new ServiceFaultException(EXCEPTION, serviceStatus);
				}
			}
		}
	}

	public void checkBillingExistence(long billingCode) {
		serviceStatus.getServiceResult().clear();
		Optional<Billing> aBill = billingRepo.findById(billingCode);
		if (!aBill.isPresent()) {
			serviceStatus.setServiceCode(NOT_FOUND);
			serviceStatus.getServiceResult().add("billing with code " + billingCode + " was not found");
			throw new ServiceFaultException(EXCEPTION, serviceStatus);
		}
	}

	public void updateFieldsValidation(UpdateBillingRequest request) {
		serviceStatus.getServiceResult().clear();
		ArrayList<Long> allProductCodes = new ArrayList<>();

		for (int i = 0; i < request.getProductDetails().size(); i++) {
			long codProduct = request.getProductDetails().get(i).getCodProduct();
			int quatity = request.getProductDetails().get(i).getQuantity();
			if (codProduct == 0 || quatity == 0) {
				serviceStatus.setServiceCode(BAD_REQUEST);
				serviceStatus.getServiceResult().add("No empty fields allowed in products details");
				throw new ServiceFaultException(EXCEPTION, serviceStatus);
			}
			Optional<Products> aProduct = billingProductsRepo.findByCodeProduct(codProduct);
			if (aProduct.isEmpty()) {
				serviceStatus.setServiceCode(NOT_FOUND);
				serviceStatus.getServiceResult()
						.add("product code " + codProduct + " was not found in DB." + "Cannot Proceed");
				throw new ServiceFaultException(EXCEPTION, serviceStatus);
			}
		}
		for (int j = 0; j < allProductCodes.size(); j++) {
			for (int k = j + 1; k < allProductCodes.size(); k++) {
				if (allProductCodes.get(j).equals(allProductCodes.get(k))) {
					serviceStatus.setServiceCode(BAD_REQUEST);
					serviceStatus.getServiceResult().add("Same code product twice or more times.");
					throw new ServiceFaultException(EXCEPTION, serviceStatus);
				}
			}
		}
	}
}
