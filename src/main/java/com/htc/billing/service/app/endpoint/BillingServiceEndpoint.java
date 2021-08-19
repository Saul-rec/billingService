package com.htc.billing.service.app.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.htc.billing.service.app.services.BillingCreateService;
import com.htc.billing.service.app.services.FindAllService;
import com.htc.billing.service.app.services.FindByBillingCodeService;
import com.htc.billing.service.generated.BillingResult;
import com.htc.billing.service.generated.DeleteBillingResponse;
import com.htc.billing.service.generated.FindAllBillingRequest;
import com.htc.billing.service.generated.FindAllBillingResponse;
import com.htc.billing.service.generated.FindByBillingCodeRequest;
import com.htc.billing.service.generated.FindByBillingCodeResponse;
import com.htc.billing.service.generated.NewBillingRequest;
import com.htc.billing.service.generated.NewBillingResponse;
import com.htc.billing.service.generated.UpdateBillingRequest;
import com.htc.billing.service.generated.UpdateBillingResponse;

@Endpoint
public class BillingServiceEndpoint {

	private static final String NAMESPACE_URI = "http://www.htc.com/billing/service/generated";
	private BillingCreateService billingServices;

	@Autowired
	private FindAllService findAllService;
	
	@Autowired
	private FindByBillingCodeService findByCodeService;
	
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
		billingResultList =  findAllService.getAllBillings();
		response.getBillingResult().addAll(billingResultList);
		return response;
	}
		

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "findByBillingCodeRequest")
	@ResponsePayload
	public FindByBillingCodeResponse findByCodeResponse(@RequestPayload FindByBillingCodeRequest request) {
		FindByBillingCodeResponse response = new FindByBillingCodeResponse();
		BillingResult result = findByCodeService.findByCode(request.getBillingCode());
		response.setBillingResult(result);
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
