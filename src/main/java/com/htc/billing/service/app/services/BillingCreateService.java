package com.htc.billing.service.app.services;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htc.billing.service.app.entities.Billing;
import com.htc.billing.service.app.entities.BillingDetails;
import com.htc.billing.service.app.entities.Employee;
import com.htc.billing.service.app.entities.Products;
import com.htc.billing.service.app.exceptions.ServiceFaultException;
import com.htc.billing.service.app.repository.BillingDetailsRepository;
import com.htc.billing.service.app.repository.BillingEmployeeRepository;
import com.htc.billing.service.app.repository.BillingProductsRepository;
import com.htc.billing.service.app.repository.BillingRepository;
import com.htc.billing.service.generated.NewBillingRequest;
import com.htc.billing.service.generated.ServiceStatus;

@Service
public class BillingCreateService {

	private static final String EXCEPTION = "Data Validation Failed. See Reasons";

	private BillingDetailsRepository billingDetailsRepo;
	private BillingRepository billingRepo;
	@Autowired
	private BillingEmployeeRepository billingEmployeeRepo;
	@Autowired
	private BillingProductsRepository billingProductsRepo;
	private ServiceStatus serviceStatus = new ServiceStatus();

	@Autowired
	public BillingCreateService(BillingDetailsRepository repository, BillingRepository billingRepository) {
		super();
		this.billingDetailsRepo = repository;
		this.billingRepo = billingRepository;
	}

	public ServiceStatus createNewBilling(NewBillingRequest request) {
		serviceStatus.getServiceResult().clear();
		Random rnd = new Random();
		long billingCode = (rnd.nextInt(89999998) + 10000000);
		long billingDetailCode = billingCode + 1; 
		
		long codEmployee = request.getCodEmployee();
		String nameClient = request.getNameClient();
		String paymentType = request.getPaymentType();
		LocalDate dateOfSell = LocalDate.now();
		double totalIva=0;
		double totalSell = 0;
		double subtotal = 0;
		Optional<Products> aProduct;
		
		double ivaPerProduct;
		double amount;
		
		long codProduct;

		Optional<Employee> anEmployee = billingEmployeeRepo.findByCodeEmployee(codEmployee);
		if (anEmployee.isEmpty()) {
			serviceStatus.setServiceCode("404: NOT FOUND");
			serviceStatus.getServiceResult().add("The employee " + codEmployee + " was not found");
			throw new ServiceFaultException(EXCEPTION, serviceStatus);
		}
		
		for (int i = 0; i < request.getProductDetails().size(); i++) {
			long codProdu = request.getProductDetails().get(i).getCodProduct();
			int quantity = request.getProductDetails().get(i).getQuantity();
			if (codProdu == 0 || quantity == 0) {
				serviceStatus.setServiceCode("500: BAD REQUEST");
				serviceStatus.getServiceResult().add("No empty fields allowed in products details");
				throw new ServiceFaultException(EXCEPTION, serviceStatus);
			}
		}

		for (int i = 0; i < request.getProductDetails().size(); i++) {
			codProduct = request.getProductDetails().get(i).getCodProduct();
			aProduct = billingProductsRepo.findByCodeProduct(codProduct);
			if (aProduct.isEmpty()) {
				serviceStatus.setServiceCode("404: NOT FOUND");
				serviceStatus.getServiceResult()
						.add("product " + codProduct + " was not found in DB." + "Cannot Proceed");
				throw new ServiceFaultException("DATA ERROR", serviceStatus);
			}
		}

		for (int i = 0; i < request.getProductDetails().size(); i++) {
			codProduct = request.getProductDetails().get(i).getCodProduct();
			int quantity = request.getProductDetails().get(i).getQuantity();
			aProduct = billingProductsRepo.findByCodeProduct(codProduct);
			
			double productUnitPrice = aProduct.get().getUnitPriceProduct();
			amount = quantity * productUnitPrice;
			ivaPerProduct = productUnitPrice * 0.13;
			subtotal += amount;
			totalIva += ivaPerProduct;
			try {
				BillingDetails details = new BillingDetails(billingDetailCode,billingCode, codProduct, quantity, 
						productUnitPrice,ivaPerProduct,amount);
				billingDetailsRepo.save(details);
				serviceStatus.getServiceResult().clear();
			} catch (Exception e) {
				serviceStatus.setServiceCode("500: SERVER ERROR");
				serviceStatus.getServiceResult().add(e.toString());
				throw new ServiceFaultException(EXCEPTION, serviceStatus);
			}
		}
		
		totalSell = subtotal + totalIva;
		try {
			Billing bill = new Billing(billingCode, codEmployee, nameClient, paymentType, dateOfSell,
					totalIva,subtotal,totalSell);
			billingRepo.save(bill);
			serviceStatus.setServiceCode("201: CREATED");
			serviceStatus.getServiceResult().add("billing with code " + billingCode + " created in DB successfully");
		} catch (Exception e) {
			serviceStatus.setServiceCode("500: SERVER ERROR");
			serviceStatus.getServiceResult().add(e.toString());
			throw new ServiceFaultException(EXCEPTION, serviceStatus);
		}

		return serviceStatus;
	}

}
