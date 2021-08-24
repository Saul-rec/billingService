package com.htc.billing.service.app.services;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htc.billing.service.app.entities.Billing;
import com.htc.billing.service.app.entities.BillingDetails;
import com.htc.billing.service.app.entities.Products;
import com.htc.billing.service.app.exceptions.ServiceFaultException;
import com.htc.billing.service.app.repository.BillingDetailsRepository;
import com.htc.billing.service.app.repository.BillingProductsRepository;
import com.htc.billing.service.app.repository.BillingRepository;
import com.htc.billing.service.generated.NewBillingRequest;
import com.htc.billing.service.generated.ServiceStatus;

@Service
public class BillingCreateService {

	private static final String EXCEPTION = "Data Validation Failed. See Reasons";

	private BillingDetailsRepository billingDetailsRepo;
	private BillingRepository billingRepo;
	ValidationService validation = new ValidationService();

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
		
		long billingCode = generateRandom();
		
		long codEmployee = request.getCodEmployee();
		String nameClient = request.getNameClient();
		String paymentType = request.getPaymentType();
		LocalDate dateOfSell = LocalDate.now();
		double totalIva = 0;
		double totalSell = 0;
		double subtotal = 0;
		Optional<Products> aProduct;

		double ivaPerProduct = 0;
		double amount = 0;
		long codProduct;
		validation.checkEmployeeExistence(codEmployee);
		validation.allProductsInCreateRequestExist(request);
		validation.noEmptyFieldInListValidation(request);

		Billing bill1 = new Billing(billingCode, codEmployee, nameClient, paymentType, dateOfSell, totalIva,
				subtotal, totalSell);
		billingRepo.save(bill1);
		for (int i = 0; i < request.getProductDetails().size(); i++) {
			long billingDetailCode = generateRandom();
			
			codProduct = request.getProductDetails().get(i).getCodProduct();
			int quantity = request.getProductDetails().get(i).getQuantity();
			aProduct = billingProductsRepo.findByCodeProduct(codProduct);
			double productUnitPrice = aProduct.get().getUnitPriceProduct();
			amount = quantity * productUnitPrice;
			ivaPerProduct = amount * 0.13;
			subtotal += amount;
			totalIva += ivaPerProduct;
			if (i == 0) {
				
			}
			try {
				BillingDetails details = new BillingDetails(billingDetailCode, billingCode, codProduct, quantity,
						productUnitPrice, ivaPerProduct, amount);
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
			Billing bill2 = new Billing(billingCode, codEmployee, nameClient, paymentType, dateOfSell, totalIva,
					subtotal, totalSell);
			billingRepo.save(bill2);
			serviceStatus.setServiceCode("201: CREATED");
			serviceStatus.getServiceResult()
					.add("billing with code " + billingCode + " created in DB successfully");
		} catch (Exception e) {
			serviceStatus.setServiceCode("500: SERVER ERROR");
			serviceStatus.getServiceResult().add(e.toString());
			throw new ServiceFaultException(EXCEPTION, serviceStatus);
		}
		return serviceStatus;
	}
	
	public long generateRandom() {
		Random rnd = new Random();
		return (rnd.nextInt(89999998) + 10000000);
	}
}
