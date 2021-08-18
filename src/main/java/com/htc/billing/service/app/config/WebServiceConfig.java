package com.htc.billing.service.app.config;

import java.util.Properties;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import com.htc.billing.service.app.exceptions.ServiceFaultException;
import com.htc.billing.service.app.exceptions.SoapFaultDefinitionExceptionResolver;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(context);
		messageDispatcherServlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<>(messageDispatcherServlet, "/ws/*");
	}

	@Bean(name = "billingService")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema billingSchema) {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		definition.setPortTypeName("billingServicePort");
		definition.setLocationUri("/ws");
		definition.setTargetNamespace("http://www.htc.com/billing/service/generated");
		definition.setSchema(billingSchema);
		return definition;
	}

	@Bean
	public XsdSchema billingSchema() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/BillingService.xsd"));
	}

	@Bean
	public SoapFaultMappingExceptionResolver exceptionResolver() {
		SoapFaultMappingExceptionResolver resolver = new SoapFaultDefinitionExceptionResolver();

		SoapFaultDefinition definition = new SoapFaultDefinition();
		definition.setFaultCode(SoapFaultDefinition.SERVER);
		resolver.setDefaultFault(definition);

		Properties errorMapping = new Properties();
		errorMapping.setProperty(Exception.class.getName(), SoapFaultDefinition.SERVER.toString());
		errorMapping.setProperty(ServiceFaultException.class.getName(), SoapFaultDefinition.SENDER.toString());
		resolver.setExceptionMappings(errorMapping);
		resolver.setOrder(1);

		return resolver;
	}

}
