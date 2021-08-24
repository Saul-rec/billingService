package com.htc.billing.service.app.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.htc.billing.service.app.entities.Billing;
import com.htc.billing.service.app.entities.BillingDetails;
import com.htc.billing.service.app.exceptions.ServiceFaultException;
import com.htc.billing.service.app.repository.BillingDetailsRepository;
import com.htc.billing.service.app.repository.BillingRepository;
import com.htc.billing.service.generated.ServiceStatus;
import com.htc.billing.service.generated.UpdateBillingRequest;

@Service
public class UpdateBillingService {
	private static final String EXCEPTION = "Data Validation Failed. See Reasons";
	private BillingDetailsRepository detailsRepo;
	private BillingRepository billingRepo;
	private ServiceStatus serviceStatus = new ServiceStatus();
	
	@Autowired
	private ValidationService validation;
	
	@Autowired
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
		double totalIva = 0;
		double totalSell = 0;
		double subtotal = 0;

		long codProduct;
		double ivaPerProduct;
		double amount;
		long billingCode = request.getBillingCode();
		

		validation.checkBillingExistence(billingCode);
		validation.checkEmployeeExistence(codEmployee);
		validation.updateFieldsValidation(request);

		Optional<Billing> aBill = billingRepo.findById(billingCode);
		dateOfSell = aBill.get().getDateOfSell();
		List<BillingDetails> allDetails=detailsRepo.findByBillingCode(billingCode);
		
		for (int i = 0; i < request.getProductDetails().size(); i++) {
			//Obtener el id de cada bill detail
			long billingDetailCode = allDetails.get(i).getBillingDetailCode();
			
			codProduct = request.getProductDetails().get(i).getCodProduct();
			int quantity = request.getProductDetails().get(i).getQuantity();
			double productUnitPrice = allDetails.get(i).getUnitPriceProduct();

			amount = quantity * productUnitPrice;
			ivaPerProduct = (amount * 0.13)/100;
			totalIva += ivaPerProduct;
			subtotal += amount;
			try {
				BillingDetails details = new BillingDetails(billingDetailCode, billingCode, codProduct, quantity,
						productUnitPrice, ivaPerProduct, amount);
				detailsRepo.save(details);
				serviceStatus.getServiceResult().clear();
			}catch (Exception e) {
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
			serviceStatus.setServiceCode("200: UPDATED");
			serviceStatus.getServiceResult().add("billing with code " + billingCode + " updated successfully");
		}catch (Exception e) {
			serviceStatus.setServiceCode("500: SERVER ERROR");
			serviceStatus.getServiceResult().add(e.toString());
			throw new ServiceFaultException(EXCEPTION, serviceStatus);
		}
		return serviceStatus;
	}
	
}
