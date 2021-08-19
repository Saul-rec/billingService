package com.htc.billing.service.app.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.htc.billing.service.app.services.BillingCreateService;
import com.htc.billing.service.app.services.TestService;
import com.htc.billing.service.generated.BillingResult;
import com.htc.billing.service.generated.DeleteBillingResponse;
import com.htc.billing.service.generated.FindAllBillingRequest;
import com.htc.billing.service.generated.FindAllBillingResponse;
import com.htc.billing.service.generated.FindBillingByIdRequest;
import com.htc.billing.service.generated.FindBillingByIdResponse;
import com.htc.billing.service.generated.NewBillingRequest;
import com.htc.billing.service.generated.NewBillingResponse;
import com.htc.billing.service.generated.UpdateBillingRequest;
import com.htc.billing.service.generated.UpdateBillingResponse;

@Endpoint
public class BillingServiceEndpoint {

	private static final String NAMESPACE_URI = "http://www.htc.com/billing/service/generated";
	private BillingCreateService billingServices;
	
//	@Autowired
//	private FindBillingService findBillingService;
	@Autowired
	private TestService serviceTest;
	
	
	@Autowired
	public BillingServiceEndpoint(BillingCreateService billingServices) {
		this.billingServices = billingServices;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "newBillingRequest")
	@ResponsePayload
	public NewBillingResponse newBillingResponse(@RequestPayload NewBillingRequest request) {
		NewBillingResponse response = new NewBillingResponse();
		response.setServiceStatus(billingServices.createNewBilling(request));
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllBillingRequest")
	@ResponsePayload
	public FindAllBillingResponse allBillingResponse(@RequestPayload FindAllBillingRequest request) {
		FindAllBillingResponse response = new FindAllBillingResponse();
		List<BillingResult> billingResultList = new ArrayList<BillingResult>();
		billingResultList =  serviceTest.getAllBillings();
		response.getBillingResult().addAll(billingResultList);
		return response;
	}
		
		//		List<Billing> billingList = findBillingService.getAllBillings();
//		
//		for (Billing billing : billingList) {
//			BillingResult result = new BillingResult();
//			BeanUtils.copyProperties(billing, result);
//			billingResultList.add(result);
//		}
		
		
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "findBillingByIdRequest")
	@ResponsePayload
	public FindBillingByIdResponse findByIdResponse(@RequestPayload FindBillingByIdRequest request) {
		FindBillingByIdResponse response = new FindBillingByIdResponse();
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateBillingRequest")
	@ResponsePayload
	public UpdateBillingResponse updateResponse(@RequestPayload UpdateBillingRequest request) {
		UpdateBillingResponse response = new UpdateBillingResponse();
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteBillingRequest")
	@ResponsePayload
	public DeleteBillingResponse deleteResponse(@RequestPayload UpdateBillingRequest request) {
		DeleteBillingResponse response = new DeleteBillingResponse();
		return response;
	}
}
