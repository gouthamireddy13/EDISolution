package com.abc.tpi.model.tpp;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable @Access(AccessType.FIELD)
public class TppDetail {
	
	@Column(name="CONTACT_NAME",nullable=false,unique=true)
	private String contactName;
	
	@Column(name="CONTACT_EMAIL",nullable=false)
	private String emailAddress;
	
	@Column(name="CONTACT_TITLE",nullable=false)
	private String title;
	
	@Column(name="CONTACT_PHONE",nullable=false)
	private String contactPhoneNumber;

	public String getContactName() {
		return contactName;
	}
	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public String getTitle() {
		return title;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
