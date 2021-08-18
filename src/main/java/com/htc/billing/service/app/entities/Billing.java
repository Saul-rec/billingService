package com.htc.billing.service.app.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Billing {

	@Id
	private long billingCode;
	
	private long codeEmployee;
	
	@Column(nullable = true)
	private String nameEmployee;
	private String nameClient;
	private String sellType;
	private LocalDate dateOfSell;
	private double totalSell;

	public Billing() {
		super();
	}

	public Billing(long billingCode, long codeEmployee,String nameEmployee,
			String nameClient, String sellType, LocalDate dateOfSell, double totalSell) {
		super();
		this.billingCode = billingCode;
		this.nameEmployee = nameEmployee;
		this.codeEmployee = codeEmployee;
		this.nameClient = nameClient;
		this.sellType = sellType;
		this.dateOfSell = dateOfSell;
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

	public String getSellType() {
		return sellType;
	}

	public void setSellType(String sellType) {
		this.sellType = sellType;
	}

	public LocalDate getDateOfSell() {
		return dateOfSell;
	}

	public void setDateOfSell(LocalDate dateOfSell) {
		this.dateOfSell = dateOfSell;
	}

	public double getTotalSell() {
		return totalSell;
	}

	public void setTotalSell(double totalSell) {
		this.totalSell = totalSell;
	}

	public String getNameEmployee() {
		return nameEmployee;
	}

	public void setNameEmployee(String nameEmployee) {
		this.nameEmployee = nameEmployee;
	}

}
