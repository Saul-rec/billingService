package com.htc.billing.service.app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private long codClient;
	private String nameClient;
	private String lastnameClient;
	private String email;

	public Client() {
		super();

	}

	public Client( long codClient, String nameClient, String lastnameClient, String email) {
		super();
		this.codClient = codClient;
		this.nameClient = nameClient;
		this.lastnameClient = lastnameClient;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public long getCodClient() {
		return codClient;
	}

	public void setCodClient(long codClient) {
		this.codClient = codClient;
	}

	public String getNameClient() {
		return nameClient;
	}

	public void setNameClient(String nameClient) {
		this.nameClient = nameClient;
	}

	public String getLastnameClient() {
		return lastnameClient;
	}

	public void setLastnameClient(String lastnameClient) {
		this.lastnameClient = lastnameClient;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
