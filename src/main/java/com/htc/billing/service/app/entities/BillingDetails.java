package com.htc.billing.service.app.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BillingDetails {

	@Id
	private long billingDetailCode;
	
	
	private long billingCode;
	private long codProduct;
	private int quantity;
	private double unitPriceProduct;
	private double ivaPerProduct;
	private double amount;

	public BillingDetails() {
		super();
	}

	public BillingDetails(long billingDetailCode, long billingCode, long codProduct, int quantity,
			double unitPriceProduct, double ivaPerProduct, double amount) {
		super();
		this.billingDetailCode = billingDetailCode;
		this.billingCode = billingCode;
		this.codProduct = codProduct;
		this.quantity = quantity;
		this.unitPriceProduct = unitPriceProduct;
		this.ivaPerProduct = ivaPerProduct;
		this.amount = amount;
	}

	public long getBillingDetailCode() {
		return billingDetailCode;
	}

	public void setBillingDetailCode(long billingDetailCode) {
		this.billingDetailCode = billingDetailCode;
	}

	public long getBillingCode() {
		return billingCode;
	}

	public void setBillingCode(long billingCode) {
		this.billingCode = billingCode;
	}

	public long getCodProduct() {
		return codProduct;
	}

	public void setCodProduct(long codProduct) {
		this.codProduct = codProduct;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getUnitPriceProduct() {
		return unitPriceProduct;
	}

	public void setUnitPriceProduct(double unitPriceProduct) {
		this.unitPriceProduct = unitPriceProduct;
	}

	public double getIvaPerProduct() {
		return ivaPerProduct;
	}

	public void setIvaPerProduct(double ivaPerProduct) {
		this.ivaPerProduct = ivaPerProduct;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
