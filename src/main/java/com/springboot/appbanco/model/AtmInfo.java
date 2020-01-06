package com.springboot.appbanco.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Document(collection = "ATMInfo")
public class AtmInfo {

	private String bankName;
	private String nameRedATM;
	private Integer numMaxDespositAtm;
	private Integer numMaxRetirementAtm;
	private double interbankComission;

	public AtmInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getNameRedATM() {
		return nameRedATM;
	}

	public void setNameRedATM(String nameRedATM) {
		this.nameRedATM = nameRedATM;
	}

	public Integer getNumMaxDespositAtm() {
		return numMaxDespositAtm;
	}

	public void setNumMaxDespositAtm(Integer numMaxDespositAtm) {
		this.numMaxDespositAtm = numMaxDespositAtm;
	}

	public Integer getNumMaxRetirementAtm() {
		return numMaxRetirementAtm;
	}

	public void setNumMaxRetirementAtm(Integer numMaxRetirementAtm) {
		this.numMaxRetirementAtm = numMaxRetirementAtm;
	}

	public double getInterbankComission() {
		return interbankComission;
	}

	public void setInterbankComission(double interbankComission) {
		this.interbankComission = interbankComission;
	}

	
}
