package com.htc.billing.service.app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private long codEmployee;
	private String nameEmployee;
	private String lastnameEmployee;

	public Employee() {
		super();
	}

	public Employee(long codEmployee, String nameEmployee, String lastnameEmployee) {
		super();
		this.codEmployee = codEmployee;
		this.nameEmployee = nameEmployee;
		this.lastnameEmployee = lastnameEmployee;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public long getCodEmployee() {
		return codEmployee;
	}

	public void setCodEmployee(long codEmployee) {
		this.codEmployee = codEmployee;
	}

	public String getNameEmployee() {
		return nameEmployee;
	}

	public void setNameEmployee(String nameEmployee) {
		this.nameEmployee = nameEmployee;
	}

	public String getLastnameEmployee() {
		return lastnameEmployee;
	}

	public void setLastnameEmployee(String lastnameEmployee) {
		this.lastnameEmployee = lastnameEmployee;
	}

}
