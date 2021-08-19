package com.htc.billing.service.app.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class BillingDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private long billingCode;
	
	@Column(nullable = false)
	private long codProduct;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column(nullable = true)
	String presentationProduct;
	private String nameProduct;
	
	@Column(nullable = false)
	private double unitPriceProduct;
	
	@Column(nullable = false, precision = 2)
	private double subtotal;

	public BillingDetails() {
		super();
	}

	
	public BillingDetails(long billingCode, long codProduct, int quantity, 
			String presentationProduct, String nameProduct, double unitPriceProduct, double subtotal) {
		super();
		this.billingCode = billingCode;
		this.codProduct = codProduct;
		this.quantity = quantity;
		this.presentationProduct = presentationProduct;
		this.nameProduct = nameProduct;
		this.unitPriceProduct = unitPriceProduct;
		this.subtotal = subtotal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPresentationProduct() {
		return presentationProduct;
	}

	public void setPresentationProduct(String presentationProduct) {
		this.presentationProduct = presentationProduct;
	}

	public String getNameProduct() {
		return nameProduct;
	}

	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}

	public double getUnitPriceProduct() {
		return unitPriceProduct;
	}

	public void setUnitPriceProduct(double unitPriceProduct) {
		this.unitPriceProduct = unitPriceProduct;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

}
