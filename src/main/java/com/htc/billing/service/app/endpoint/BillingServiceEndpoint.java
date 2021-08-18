package com.htc.billing.service.app.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.htc.billing.service.app.services.BillingCreateService;
import com.htc.billing.service.generated.NewBillingRequest;
import com.htc.billing.service.generated.NewBillingResponse;

@Endpoint
public class BillingServiceEndpoint {

	private static final String NAMESPACE_URI = "http://www.htc.com/billing/service/generated";
	private BillingCreateService billingServices;
	
	@Autowired
	public BillingServiceEndpoint(BillingCreateService billingServices) {
		this.billingServices = billingServices;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "newBillingRequest")
	@ResponsePayload
	public NewBillingResponse response(@RequestPayload NewBillingRequest request) {
		NewBillingResponse response = new NewBillingResponse();
		response.setServiceStatus(billingServices.createNewBilling(request));
		return response;
	}
	
}
