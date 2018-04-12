package com.hss.map.model;

public class UserModel {
	private int id;
	private String name;
	private String password;
	private int adm;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAdm() {
		return adm;
	}
	public void setAdm(int adm) {
		this.adm = adm;
	}
	
}
