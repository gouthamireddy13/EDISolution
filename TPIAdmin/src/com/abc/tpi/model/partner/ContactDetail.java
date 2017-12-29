package com.abc.tpi.model.partner;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.abc.tpi.model.master.Document;

@Embeddable
@Entity
@Table(name="ABC_TPI_CONTACT_DETAIL")

public class ContactDetail{
	
	@Id
	@SequenceGenerator(name="ABC_TPI_CONTACT_DETAIL_GEN", sequenceName="ABC_TPI_CONTACT_DETAIL_SEQ",allocationSize=10)
	@GeneratedValue(generator="ABC_TPI_CONTACT_DETAIL_GEN")
	@Column(name="CONTACT_DETAIL_ID")
	private Long id;
	
	@NotNull
	@Column(name="NAME", nullable=false)
	private String contactName;
	
	@Column(name="TITLE", nullable=true)	
	private String contactTitle;
		
	@Column(name="BUSINESS_PHONE", nullable=true)
	private String businessPhone;
	
	@Column(name="BUSINESS_COUNTRY", nullable=true)
	private String businessPhoneCountry;
	
	@Column(name="MOBILE_PHONE", nullable=true)
	private String mobilePhone;
	
	@Column(name="MOBILE_COUNTRY", nullable=true)
	private String mobilePhoneCountry;
	
	@Column(name="PERSONAL_COUNTRY", nullable=true)
	private String personalPhoneCountry;
	
	@Column(name="PERSONAL_PHONE", nullable=true)
	private String personalPhone;
	
	@NotNull
	@Column(name="EMAIL", nullable=false)
	private String contactEmail;
	
	@Column(name="EMAIL2", nullable=true)
	private String contactEmail2;

	@Column(name="BUSINESS_EXT", nullable=true)
	private String businessPhoneExt;
	
	@Column(name="MOBILE_EXT", nullable=true)
	private String mobilePhoneExt;
	
	@Column(name="PERSONAL_EXT", nullable=true)
	private String personalPhoneExt;
	
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="DOC_ID", nullable=false)
	private Document transactionType;
	
	public String getBusinessPhone() {
		return businessPhone;
	}
	public String getBusinessPhoneCountry() {
		return businessPhoneCountry;
	}
	public String getBusinessPhoneExt() {
		return businessPhoneExt;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	
	public String getContactEmail2() {
		return contactEmail2;
	}
	
	public String getContactName() {
		return contactName;
	}
	public String getContactTitle() {
		return contactTitle;
	}
	public Long getId() {
		return id;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public String getMobilePhoneCountry() {
		return mobilePhoneCountry;
	}
	public String getMobilePhoneExt() {
		return mobilePhoneExt;
	}
	public String getPersonalPhone() {
		return personalPhone;
	}
	public String getPersonalPhoneCountry() {
		return personalPhoneCountry;
	}
	public String getPersonalPhoneExt() {
		return personalPhoneExt;
	}
	public Document getTransactionType() {
		return transactionType;
	}
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}
	public void setBusinessPhoneCountry(String businessPhoneCountry) {
		this.businessPhoneCountry = businessPhoneCountry;
	}
	public void setBusinessPhoneExt(String businessPhoneExt) {
		this.businessPhoneExt = businessPhoneExt;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public void setContactEmail2(String contactEmail2) {
		this.contactEmail2 = contactEmail2;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public void setMobilePhoneCountry(String mobilePhoneCountry) {
		this.mobilePhoneCountry = mobilePhoneCountry;
	}
	public void setMobilePhoneExt(String mobilePhoneExt) {
		this.mobilePhoneExt = mobilePhoneExt;
	}
	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}
	public void setPersonalPhoneCountry(String personalPhoneCountry) {
		this.personalPhoneCountry = personalPhoneCountry;
	}
	public void setPersonalPhoneExt(String personalPhoneExt) {
		this.personalPhoneExt = personalPhoneExt;
	}
	public void setTransactionType(Document transactionType) {
		this.transactionType = transactionType;
	}

	
	public static ContactDetail newInstance(ContactDetail contactDetail)
	{

		if(contactDetail == null)
		{
			return null;
		}
		
		ContactDetail clonedContact = new ContactDetail();
		
		clonedContact.setContactName(contactDetail.getContactName());
		clonedContact.setContactTitle(contactDetail.getContactTitle());
		clonedContact.setBusinessPhone(contactDetail.getBusinessPhone());
		clonedContact.setBusinessPhoneCountry(contactDetail.getBusinessPhoneCountry());
		clonedContact.setMobilePhone(contactDetail.getMobilePhone());
		clonedContact.setMobilePhoneCountry(contactDetail.getMobilePhoneCountry());
		clonedContact.setPersonalPhoneCountry(contactDetail.getPersonalPhoneCountry());
		clonedContact.setPersonalPhone(contactDetail.getPersonalPhone());
		clonedContact.setContactEmail(contactDetail.getContactEmail());
		clonedContact.setContactEmail2(contactDetail.getContactEmail2());
		clonedContact.setBusinessPhoneExt(contactDetail.getBusinessPhoneExt());
		clonedContact.setMobilePhoneExt(contactDetail.getMobilePhoneExt());
		clonedContact.setPersonalPhoneExt(contactDetail.getPersonalPhoneExt());
		clonedContact.setTransactionType(Document.newInstance(contactDetail.getTransactionType()));
		
		return clonedContact;
	}	
}
