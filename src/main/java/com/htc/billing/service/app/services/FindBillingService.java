package com.htc.billing.service.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htc.billing.service.app.entities.Billing;
import com.htc.billing.service.app.entities.BillingDetails;
import com.htc.billing.service.app.repository.BillingDetailsRepository;
import com.htc.billing.service.app.repository.BillingRepository;
import com.htc.billing.service.generated.BillingResult;

@Service
public class FindBillingService {

	private BillingDetailsRepository detailsRepo;
	private BillingRepository billingRepo;
	private List<Billing> allBillings;
	private List<BillingDetails> allBillingDetails;
	private long billingCode;

	@Autowired
	public FindBillingService(BillingDetailsRepository detailsRepo, BillingRepository billingRepo) {
		super();
		this.detailsRepo = detailsRepo;
		this.billingRepo = billingRepo;
	}

	public List<Billing> getAllBillings() {
		allBillings = new ArrayList<>();
		billingRepo.findAll().forEach(e -> allBillings.add(e));
		BillingResult result = new BillingResult();
		List<BillingResult.ProductsDetails> billPDetails = new ArrayList<BillingResult.ProductsDetails>();
		allBillingDetails = new ArrayList<>();

		for (int i = 0; i < allBillings.size(); i++) {
			billingCode = allBillings.get(i).getBillingCode();
			detailsRepo.findByBillingCode(billingCode).forEach(e -> allBillingDetails.add(e));
			for (BillingDetails billingDetails : allBillingDetails) {
				System.out.println(billingDetails);
				BillingResult.ProductsDetails pdetails = new BillingResult.ProductsDetails();
				BeanUtils.copyProperties(billingDetails, pdetails);
				billPDetails.add(pdetails);
			}

		}
		result.getProductsDetails().addAll(billPDetails);
		return allBillings;
	}
}
