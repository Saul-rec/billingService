package com.htc.billing.service.app.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

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
import com.htc.billing.service.generated.BillingResult;
import com.htc.billing.service.generated.ServiceStatus;

@Service
public class FindByBillingCodeService {
	private BillingDetailsRepository detailsRepo;
	private BillingRepository billingRepo;
	private static final String EXCEPTION = "Exception Generated. See details";
	private Optional<Billing> aBilling;
	private List<BillingDetails> allBillingDetails;
	
	private XMLGregorianCalendar sellDate;
	private ServiceStatus serviceStatus = new ServiceStatus();

	@Autowired
	private BillingEmployeeRepository employeeRepo;
	
	@Autowired
	private BillingProductsRepository productRepo;
	
	@Autowired
	public FindByBillingCodeService(BillingDetailsRepository detailsRepo, BillingRepository billingRepo) {
		this.detailsRepo = detailsRepo;
		this.billingRepo = billingRepo;
	}

	public BillingResult findByCode(long billingCode) {
		
		long billCode = billingCode;
		aBilling = billingRepo.findById(billCode);
		if (!aBilling.isPresent()) {
			serviceStatus.setServiceCode("404: NOT FOUND");
			serviceStatus.getServiceResult().add("Billing with code:" + billCode + " not found in DB");
			throw new ServiceFaultException("DATA ERROR", serviceStatus);
		}
		long employeeCode = aBilling.get().getCodeEmployee();
		Optional<Employee> anEmp = employeeRepo.findByCodeEmployee(employeeCode);
		String fullnameEmployee = anEmp.get().getNameEmployee() + " " + anEmp.get().getLastnameEmployee();
		
		BillingResult results = new BillingResult();

		results.setBillingCode(billCode);
		results.setCodeEmployee(aBilling.get().getCodeEmployee());
		results.setNameEmployee(fullnameEmployee);
		results.setNameClient(aBilling.get().getNameClient());
		results.setPaymentType(aBilling.get().getPaymentType());

		LocalDate dateofsell = LocalDate.parse(aBilling.get().getDateOfSell().toString());
		try {
			sellDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateofsell.toString());
		} catch (DatatypeConfigurationException e) {
			serviceStatus.setServiceCode("500: SERVER ERROR");
			serviceStatus.getServiceResult().add(e.toString());
			throw new ServiceFaultException(EXCEPTION, serviceStatus);
		}
		results.setIva(aBilling.get().getTotalIva());
		results.setSubtotal(aBilling.get().getSubtotal());
		results.setDateOfSell(sellDate);
		results.setTotalSell(aBilling.get().getTotalSell());

		allBillingDetails = new ArrayList<>();
		detailsRepo.findByBillingCode(billCode).forEach(e -> allBillingDetails.add(e));

		for (BillingDetails billDetailElement : allBillingDetails) {
			BillingResult.ProductsDetails pdetails = new BillingResult.ProductsDetails();
			long codProduct = billDetailElement.getCodProduct();
			Optional<Products> aProduct = productRepo.findByCodeProduct(codProduct);
			
			pdetails.setCodProduct(codProduct);
			pdetails.setQuantity(billDetailElement.getQuantity());
			pdetails.setPresentationProduct(aProduct.get().getPresentation());
			pdetails.setNameProduct(aProduct.get().getNameProduct());
			pdetails.setUnitPriceProduct(billDetailElement.getUnitPriceProduct());
			results.getProductsDetails().add(pdetails);
		}
		return results;
	}

}
