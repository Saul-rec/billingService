package com.htc.billing.service.app.entities;



public class Employee {

	
	private long codeEmployee;
	private String nameEmployee;
	private String lastnameEmployee;

	
	public Employee() {
		super();
	}


	public Employee(long codeEmployee, String nameEmployee, String lastnameEmployee) {
		super();
		this.codeEmployee = codeEmployee;
		this.nameEmployee = nameEmployee;
		this.lastnameEmployee = lastnameEmployee;
	}


	public long getCodeEmployee() {
		return codeEmployee;
	}


	public void setCodeEmployee(long codeEmployee) {
		this.codeEmployee = codeEmployee;
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
