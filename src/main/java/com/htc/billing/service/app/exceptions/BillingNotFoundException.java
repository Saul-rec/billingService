package com.htc.billing.service.app.exceptions;

public class BillingNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	public static final String NAMESPACE_URI = "http://www.htc.com/billing/service/generated";

	public BillingNotFoundException(String message) {
		super(message);
	}

}
