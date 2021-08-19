package com.htc.billing.service.app.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htc.billing.service.app.entities.Billing;
import com.htc.billing.service.app.entities.BillingDetails;
import com.htc.billing.service.app.exceptions.ServiceFaultException;
import com.htc.billing.service.app.repository.BillingDetailsRepository;
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
		   
			billingCode = billElement.getBillingCode();
			results.setBillingCode(billingCode);
			results.setCodeEmployee(billElement.getCodeEmployee());
			results.setNameEmployee(billElement.getNameEmployee());
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
			results.setTotalSell(billElement.getTotalSell());

			allBillingDetails = new ArrayList<>();
			detailsRepo.findByBillingCode(billingCode).forEach(e -> allBillingDetails.add(e));

			for (BillingDetails billDetailElement : allBillingDetails) {
				BillingResult.ProductsDetails pdetails = new BillingResult.ProductsDetails();
				BeanUtils.copyProperties(billDetailElement, pdetails);
				results.getProductsDetails().add(pdetails);
			}
			billingObjectList.add(results);
		}
		return billingObjectList;
	}

}
