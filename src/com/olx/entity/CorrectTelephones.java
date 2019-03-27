package com.olx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="SouthAfrican_Telephones")
public class CorrectTelephones {
	@Id
	@Column(name="id")
	private String id;
	@Column(name="telephone")
	private String telephone;
	@Column(name="isfixed")
	private String isfixed;
	public CorrectTelephones(String id, String telephone, String isfixed) {
		// TODO Auto-generated constructor stub
		super();
		this.id = id;
		this.telephone = telephone;
		this.isfixed = isfixed;
	}
	public CorrectTelephones() {

	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getIsfixed() {
		return isfixed;
	}
	public void setIsfixed(String isfixed) {
		this.isfixed = isfixed;
	}
}
