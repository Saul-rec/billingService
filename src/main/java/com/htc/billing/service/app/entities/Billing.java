package com.htc.billing.service.app.entities;

import java.time.LocalDate;

public class Billing {


	private long billingCode;
	private long codeEmployee;
	private String nameClient;
	private String paymentType;
	private LocalDate dateOfSell;
	private double totalIva;
	private double subtotal;
	private double totalSell;

	public Billing() {
		super();
	}
	
	public Billing(long billingCode, long codeEmployee, String nameClient, String paymentType, LocalDate dateOfSell,
			double totalIva, double subtotal, double totalSell) {
		super();
		this.billingCode = billingCode;
		this.codeEmployee = codeEmployee;
		this.nameClient = nameClient;
		this.paymentType = paymentType;
		this.dateOfSell = dateOfSell;
		this.totalIva = totalIva;
		this.subtotal = subtotal;
		this.totalSell = totalSell;
	}




	public long getBillingCode() {
		return billingCode;
	}

	public void setBillingCode(long billingCode) {
		this.billingCode = billingCode;
	}

	public long getCodeEmployee() {
		return codeEmployee;
	}

	public void setCodeEmployee(long codeEmployee) {
		this.codeEmployee = codeEmployee;
	}

	public String getNameClient() {
		return nameClient;
	}

	public void setNameClient(String nameClient) {
		this.nameClient = nameClient;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public LocalDate getDateOfSell() {
		return dateOfSell;
	}

	public void setDateOfSell(LocalDate dateOfSell) {
		this.dateOfSell = dateOfSell;
	}

	
	public double getTotalIva() {
		return totalIva;
	}

	public void setTotal_iva(double totalIva) {
		this.totalIva = totalIva;
	}


	public double getSubtotal() {
		return subtotal;
	}


	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}


	public double getTotalSell() {
		return totalSell;
	}

	public void setTotalSell(double totalSell) {
		this.totalSell = totalSell;
	}



}
