package com.mete.infonal.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * MongoDB keeps all data in documents, so User class have @Document annotation
 */

@Document
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
    private String id;
    private String name;
    private String lastName;
    private String phoneNumber;
    
    public User() {
		// TODO Auto-generated method stub

	}
    public User(String name, String lastName, String phoneNumber) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
