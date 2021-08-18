package com.htc.billing.service.app.exceptions;

import com.htc.billing.service.generated.ServiceStatus;

public class ServiceFaultException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;
	
	private ServiceStatus serviceStatus;

	public ServiceFaultException(String message, ServiceStatus serviceStatus) {
		super(message);
		this.serviceStatus = serviceStatus;
	}

	public ServiceFaultException(String message, Throwable e, ServiceStatus serviceStatus) {
		super(message, e);
		this.serviceStatus = serviceStatus;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}


}
