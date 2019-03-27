package com.olx.models;

public class TelephoneModel {
	private String telephone;
	private String status;
	public TelephoneModel() {
		
	}
	
	public TelephoneModel(String telephone,String status) {
		super();
		this.telephone = telephone;
		this.status = status;
	}
	

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
