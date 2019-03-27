package com.olx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Incorrect_SouthAfrican_Telephones")
public class IncorrectTelephones {
	@Id
	@Column(name="id")
	private String id;
	@Column(name="telephone")
	private String telephone;
	public IncorrectTelephones(String id, String telephone) {
		// TODO Auto-generated constructor stub
		super();
		this.id = id;
		this.telephone = telephone;

	}
	public IncorrectTelephones() {

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

}
