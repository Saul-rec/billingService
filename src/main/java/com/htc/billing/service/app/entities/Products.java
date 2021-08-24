package com.htc.billing.service.app.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Products {

	@Id
	private long codeProduct;
	
	private String descProduct;
	private String nameProduct;
	private String presentation;
	private int stock;
	private double unitPriceProduct;
	
	
	public Products() {
		super();

	}


	public Products(long codeProduct, String descProduct, String nameProduct, String presentation, int stock,
			double unitPriceProduct) {
		super();
		this.codeProduct = codeProduct;
		this.descProduct = descProduct;
		this.nameProduct = nameProduct;
		this.presentation = presentation;
		this.stock = stock;
		this.unitPriceProduct = unitPriceProduct;
	}


	public long getCodeProduct() {
		return codeProduct;
	}


	public void setCodeProduct(long codeProduct) {
		this.codeProduct = codeProduct;
	}


	public String getDescProduct() {
		return descProduct;
	}


	public void setDescProduct(String descProduct) {
		this.descProduct = descProduct;
	}


	public String getNameProduct() {
		return nameProduct;
	}


	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}


	public String getPresentation() {
		return presentation;
	}


	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}


	public int getStock() {
		return stock;
	}


	public void setStock(int stock) {
		this.stock = stock;
	}


	public double getUnitPriceProduct() {
		return unitPriceProduct;
	}


	public void setUnitPriceProduct(double unitPriceProduct) {
		this.unitPriceProduct = unitPriceProduct;
	}

	

}
