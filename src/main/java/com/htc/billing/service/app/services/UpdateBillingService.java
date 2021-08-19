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

	private BillingDetailsRepository detailsRepo;
	private BillingRepository billingRepo;
	private ServiceStatus serviceStatus =  new ServiceStatus();
	private Optional<Billing> aBill;
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
		String nameEmp = null;
		long codEmployee = request.getCodEmployee();
		String nameClient = request.getNameClient();
		String paymentType = request.getPaymentType();
		LocalDate dateOfSell = LocalDate.now();
		Optional<Products> aProduct;
		double totalSell = 0;

		double subtotal = 0;
		long codProduct;
		String presentationProduct;

		long billingCode = request.getBillingCode();
		aBill = billingRepo.findById(billingCode);
		if (!aBill.isPresent()) {
			serviceStatus.setServiceCode("404: NOT FOUND");
			serviceStatus.getServiceResult().add("billing with code " + billingCode + " was not found");
			throw new ServiceFaultException("DATA ERROR", serviceStatus);
		}

		Optional<Employee> anEmployee = billingEmployeeRepo.findByCodEmployee(codEmployee);
		if (anEmployee.isEmpty()) {
			serviceStatus.setServiceCode("404: NOT FOUND");
			serviceStatus.getServiceResult().add("The employee " + codEmployee + " was not found");
			throw new ServiceFaultException(EXCEPTION, serviceStatus);
		} else {
			nameEmp = anEmployee.get().getNameEmployee() + " " + anEmployee.get().getLastnameEmployee();
		}

		for (int i = 0; i < request.getProductDetails().size(); i++) {
			long codProdu = request.getProductDetails().get(i).getCodProduct();
			int quatity = request.getProductDetails().get(i).getQuantity();
			String prodName = request.getProductDetails().get(i).getProductName();
			double prodPrice = request.getProductDetails().get(i).getProductUnitPrice();
			if (codProdu == 0 || quatity == 0 || prodName == null || prodPrice == 0) {
				serviceStatus.setServiceCode("500: BAD REQUEST");
				serviceStatus.getServiceResult().add("No empty fields allowed in products details section");
				throw new ServiceFaultException(EXCEPTION, serviceStatus);
			}
		}

		for (int i = 0; i < request.getProductDetails().size(); i++) {
			codProduct = request.getProductDetails().get(i).getCodProduct();
			aProduct = billingProductsRepo.findByCodProduct(codProduct);
			if (aProduct.isEmpty()) {
				serviceStatus.setServiceCode("404: NOT FOUND");
				serviceStatus.getServiceResult()
						.add("product code " + codProduct + " was not found in DB." + "Cannot Proceed");
				throw new ServiceFaultException("DATA ERROR", serviceStatus);
			}
		}


		detailsRepo.deleteAllByBillingCode(billingCode);
		for (int i = 0; i < request.getProductDetails().size(); i++) {
			codProduct = request.getProductDetails().get(i).getCodProduct();
			int quantity = request.getProductDetails().get(i).getQuantity();
			presentationProduct = billingProductsRepo.findByCodProduct(codProduct).get().getPresentationProduct();
			String productName = request.getProductDetails().get(i).getProductName();
			double productUnitPrice = request.getProductDetails().get(i).getProductUnitPrice();
			subtotal = quantity * productUnitPrice;
			totalSell += subtotal;
			try {
				BillingDetails details = new BillingDetails(billingCode, codProduct, quantity, presentationProduct,
						productName, productUnitPrice, subtotal);
				detailsRepo.save(details);
				serviceStatus.getServiceResult().clear();
			} catch (Exception e) {
				serviceStatus.setServiceCode("500: SERVER ERROR");
				serviceStatus.getServiceResult().add(e.toString());
				throw new ServiceFaultException(EXCEPTION, serviceStatus);
			}
		}

		try {
			Billing bill = new Billing(billingCode, codEmployee, nameEmp, nameClient, paymentType, dateOfSell,
					totalSell);
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
