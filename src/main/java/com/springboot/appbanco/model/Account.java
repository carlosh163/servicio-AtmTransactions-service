package com.springboot.appbanco.model;

import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;


public class Account {


	private String ProductType; // Cuenta Bancarias o Producto Credito.

	private String accountType; // C.B = Ahorro, CU.Corriente, CU.PlazoFijo. -- CRED = Personal, Empresarial,
								// Tarjeta Credito, Adelanto Efectivo

	// @Size(min = 3,message ="Tipo de Producto debe tener minimo 3 caracteres")
	private Integer accountNumber;

	// @JsonSerialize(using = ToStringSerializer.class)

	@JsonFormat(pattern = "dd-MM-yyyy", shape = Shape.STRING)
	private Date openingDate; // Fecha Apertura

	private double balance; // saldo

	//Atributos PROYECTO 2:
  private Integer numMaxDesposit;
  private Integer numMaxRetirement;
  
  private String bankName;

	private char accountstatus; // Activo o Inactivo.


	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	

	public char getAccountstatus() {
		return accountstatus;
	}

	public void setAccountstatus(char accountstatus) {
		this.accountstatus = accountstatus;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getProductType() {
		return ProductType;
	}

	public void setProductType(String productType) {
		ProductType = productType;
	}

	public Integer getNumMaxDesposit() {
		return numMaxDesposit;
	}

	public void setNumMaxDesposit(Integer numMaxDesposit) {
		this.numMaxDesposit = numMaxDesposit;
	}

	public Integer getNumMaxRetirement() {
		return numMaxRetirement;
	}

	public void setNumMaxRetirement(Integer numMaxRetirement) {
		this.numMaxRetirement = numMaxRetirement;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	
	
	
	

}
