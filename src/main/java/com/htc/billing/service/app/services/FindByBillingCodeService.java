package com.htc.billing.service.app.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
public class FindByBillingCodeService {
	private BillingDetailsRepository detailsRepo;
	private BillingRepository billingRepo;
	private static final String EXCEPTION = "Exception Generated. See details";
	private Optional<Billing> aBilling;
	private List<BillingDetails> allBillingDetails;
	
	private XMLGregorianCalendar sellDate;
	private ServiceStatus serviceStatus = new ServiceStatus();

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

		BillingResult results = new BillingResult();

		results.setBillingCode(billCode);
		results.setCodeEmployee(aBilling.get().getCodeEmployee());
		results.setNameEmployee(aBilling.get().getNameEmployee());
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
		results.setDateOfSell(sellDate);
		results.setTotalSell(aBilling.get().getTotalSell());

		allBillingDetails = new ArrayList<>();
		detailsRepo.findByBillingCode(billCode).forEach(e -> allBillingDetails.add(e));

		for (BillingDetails billDetailElement : allBillingDetails) {
			BillingResult.ProductsDetails pdetails = new BillingResult.ProductsDetails();
			BeanUtils.copyProperties(billDetailElement, pdetails);
			results.getProductsDetails().add(pdetails);
		}
		return results;
	}

}
