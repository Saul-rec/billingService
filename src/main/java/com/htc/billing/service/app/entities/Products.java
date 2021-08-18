package com.htc.billing.service.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private long codProduct;
	private String nameProduct;
	
	@Column(nullable = true)
	private String descProduct;
	private double unitPriceProduct;
	
	@Column(nullable = true)
	private String presentationProduct;
	private int stockProduct;

	public Products() {
		super();

	}

	public Products(long codProduct, String nameProduct, String descProduct, double unitPriceProduct,
			String presentationProduct, int stockProduct) {
		super();
		this.codProduct = codProduct;
		this.nameProduct = nameProduct;
		this.descProduct = descProduct;
		this.unitPriceProduct = unitPriceProduct;
		this.presentationProduct = presentationProduct;
		this.stockProduct = stockProduct;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getCodProduct() {
		return codProduct;
	}

	public void setCodProduct(long codProduct) {
		this.codProduct = codProduct;
	}

	public String getNameProduct() {
		return nameProduct;
	}

	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}

	public String getDescProduct() {
		return descProduct;
	}

	public void setDescProduct(String descProduct) {
		this.descProduct = descProduct;
	}

	public double getUnitPriceProduct() {
		return unitPriceProduct;
	}

	public void setUnitPriceProduct(double unitPriceProduct) {
		this.unitPriceProduct = unitPriceProduct;
	}

	public String getPresentationProduct() {
		return presentationProduct;
	}

	public void setPresentationProduct(String presentationProduct) {
		this.presentationProduct = presentationProduct;
	}

	public int getStockProduct() {
		return stockProduct;
	}

	public void setStockProduct(int stockProduct) {
		this.stockProduct = stockProduct;
	}

}
