package com.htc.billing.service.app.exceptions;

import javax.xml.namespace.QName;

import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import com.htc.billing.service.generated.ServiceStatus;

public class SoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {

	private static final QName CODE = new QName("serviceCode");
	private static final QName MESSAGE = new QName("serviceResult");

	
	@Override
	public void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
		if (ex instanceof ServiceFaultException) {
			ServiceStatus status = ((ServiceFaultException) ex).getServiceStatus();
			SoapFaultDetail detail = fault.addFaultDetail();
			detail.addFaultDetailElement(CODE).addText(status.getServiceCode());
			for(int i = 0; i < status.getServiceResult().size(); i++) {
				detail.addFaultDetailElement(MESSAGE).addText(status.getServiceResult().get(i));	
			}
		}
	}
	
}
