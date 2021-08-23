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
public class FindAllService {

	private static final String EXCEPTION = "Exception Generated. See details";
	private BillingDetailsRepository detailsRepo;
	private BillingRepository billingRepo;
	private List<Billing> allBillings;
	private List<BillingDetails> allBillingDetails;
	private List<BillingResult> billingObjectList = new ArrayList<BillingResult>();
	private long billingCode;

	private XMLGregorianCalendar sellDate;
	private ServiceStatus serviceStatus = new ServiceStatus();

	@Autowired
	private BillingEmployeeRepository employeeRepo;
	
	@Autowired
	private BillingProductsRepository productRepo;
	
	@Autowired
	public FindAllService(BillingDetailsRepository detailsRepo, BillingRepository billingRepo) {
		super();
		this.detailsRepo = detailsRepo;
		this.billingRepo = billingRepo;
	}

	public List<BillingResult> getAllBillings() {
		billingObjectList.clear();
		allBillings = new ArrayList<>();
		billingRepo.findAll().forEach(e -> allBillings.add(e));
		for (Billing billElement : allBillings) {
		    BillingResult results = new BillingResult();
		   
		    long employeeCode = billElement.getCodeEmployee();
			Optional<Employee> anEmp = employeeRepo.findByCodeEmployee(employeeCode);
			String fullnameEmployee = anEmp.get().getNameEmployee() + " " + anEmp.get().getLastnameEmployee();
			
			billingCode = billElement.getBillingCode();
			results.setBillingCode(billingCode);
			results.setCodeEmployee(billElement.getCodeEmployee());
			results.setNameEmployee(fullnameEmployee);
			results.setNameClient(billElement.getNameClient());
			results.setPaymentType(billElement.getPaymentType());
			LocalDate dateofsell = LocalDate.parse(billElement.getDateOfSell().toString());
			try {
				sellDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateofsell.toString());
			} catch (DatatypeConfigurationException e) {
				serviceStatus.setServiceCode("500: SERVER ERROR");
				serviceStatus.getServiceResult().add(e.toString());
				throw new ServiceFaultException(EXCEPTION, serviceStatus);

			}
			results.setDateOfSell(sellDate);
			results.setIva(billElement.getTotalIva());
			results.setSubtotal(billElement.getSubtotal());
			results.setTotalSell(billElement.getTotalSell());

			allBillingDetails = new ArrayList<>();
			detailsRepo.findByBillingCode(billingCode).forEach(e -> allBillingDetails.add(e));

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
			billingObjectList.add(results);
		}
		return billingObjectList;
	}

}
