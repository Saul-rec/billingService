package com.htc.billing.service.app.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.htc.billing.service.app.entities.Billing;
import com.htc.billing.service.app.entities.BillingDetails;
import com.htc.billing.service.app.entities.Employee;
import com.htc.billing.service.app.entities.Products;
import com.htc.billing.service.app.exceptions.ServiceFaultException;
import com.htc.billing.service.app.repository.BillingDetailsRepository;
import com.htc.billing.service.app.repository.BillingEmployeeRepository;
import com.htc.billing.service.app.repository.BillingProductsRepository;
import com.htc.billing.service.app.repository.BillingRepository;
import com.htc.billing.service.generated.ServiceStatus;
import com.htc.billing.service.generated.UpdateBillingRequest;

@Service
@Transactional
public class UpdateBillingService {
	private static final String EXCEPTION = "Data Validation Failed. See Reasons";
	private static final String NOT_FOUND = "404: NOT FOUND";
	private BillingDetailsRepository detailsRepo;
	private BillingRepository billingRepo;
	private ServiceStatus serviceStatus =  new ServiceStatus();
	
	@Autowired
	private BillingEmployeeRepository billingEmployeeRepo;
	@Autowired
	private BillingProductsRepository billingProductsRepo;

	public UpdateBillingService(BillingDetailsRepository detailsRepo, BillingRepository billingRepo) {
		super();
		this.detailsRepo = detailsRepo;
		this.billingRepo = billingRepo;
	}

	@Transactional
	public ServiceStatus updateBilling(UpdateBillingRequest request) {
		long codEmployee = request.getCodEmployee();
		String nameClient = request.getNameClient();
		String paymentType = request.getPaymentType();
		LocalDate dateOfSell;
		double totalIva=0;
		double totalSell = 0;
		double subtotal = 0;
		Optional<Products> aProduct;
		
		long codProduct;
		double ivaPerProduct;
		double amount;
		
		long billingCode = request.getBillingCode();
		long billingDetailCode = billingCode + 1;
	    Optional<Billing> aBill = billingRepo.findById(billingCode);
		if (!aBill.isPresent()) {
			serviceStatus.setServiceCode(NOT_FOUND);
			serviceStatus.getServiceResult().add("billing with code " + billingCode + " was not found");
			throw new ServiceFaultException("DATA ERROR", serviceStatus);
		}else {
			dateOfSell = aBill.get().getDateOfSell();
		}

		Optional<Employee> anEmployee = billingEmployeeRepo.findByCodeEmployee(codEmployee);
		if (anEmployee.isEmpty()) {
			serviceStatus.setServiceCode(NOT_FOUND);
			serviceStatus.getServiceResult().add("The employee " + codEmployee + " was not found");
			throw new ServiceFaultException(EXCEPTION, serviceStatus);
		}

		for (int i = 0; i < request.getProductDetails().size(); i++) {
			long codProdu = request.getProductDetails().get(i).getCodProduct();
			int quatity = request.getProductDetails().get(i).getQuantity();
			if (codProdu == 0 || quatity == 0 ) {
				serviceStatus.setServiceCode("500: BAD REQUEST");
				serviceStatus.getServiceResult().add("No empty fields allowed in products details section");
				throw new ServiceFaultException(EXCEPTION, serviceStatus);
			}
		}

		for (int i = 0; i < request.getProductDetails().size(); i++) {
			codProduct = request.getProductDetails().get(i).getCodProduct();
			aProduct = billingProductsRepo.findByCodeProduct(codProduct);
			if (aProduct.isEmpty()) {
				serviceStatus.setServiceCode(NOT_FOUND);
				serviceStatus.getServiceResult()
						.add("product code " + codProduct + " was not found in DB." + "Cannot Proceed");
				throw new ServiceFaultException("DATA ERROR", serviceStatus);
			}
		}

		detailsRepo.deleteAllByBillingCode(billingCode);
		for (int i = 0; i < request.getProductDetails().size(); i++) {
			codProduct = request.getProductDetails().get(i).getCodProduct();
			Optional<BillingDetails> aBillingDetail = detailsRepo.findOneBD(
					billingDetailCode, billingCode, codProduct);
			
			int quantity = request.getProductDetails().get(i).getQuantity();
			double productUnitPrice = aBillingDetail.get().getUnitPriceProduct();
			amount = quantity * productUnitPrice;
			ivaPerProduct = productUnitPrice * 0.13;
			totalIva += ivaPerProduct;
			subtotal += amount;
			try {
				BillingDetails details = new BillingDetails(billingDetailCode,billingCode, codProduct, 
						quantity,productUnitPrice, ivaPerProduct, amount);
				detailsRepo.save(details);
				serviceStatus.getServiceResult().clear();
			} catch (Exception e) {
				serviceStatus.setServiceCode("500: SERVER ERROR");
				serviceStatus.getServiceResult().add(e.toString());
				throw new ServiceFaultException(EXCEPTION, serviceStatus);
			}
		}


		totalSell = subtotal + totalIva;
		try {
			Billing bill = new Billing(billingCode,codEmployee, nameClient, 
					paymentType, dateOfSell,totalIva,subtotal,totalSell);
			billingRepo.save(bill);
			serviceStatus.setServiceCode("200: UPDATED");
			serviceStatus.getServiceResult().add("billing with code " + billingCode + " updated successfully");
		} catch (Exception e) {
			serviceStatus.setServiceCode("500: SERVER ERROR");
			serviceStatus.getServiceResult().add(e.toString());
			throw new ServiceFaultException(EXCEPTION, serviceStatus);
		}

		return serviceStatus;
	}

}
