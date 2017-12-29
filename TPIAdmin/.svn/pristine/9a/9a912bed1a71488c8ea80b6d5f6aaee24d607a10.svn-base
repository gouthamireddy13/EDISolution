package com.abc.tpi.model.partner;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="ABC_TPI_PARTNER")
public class Partner {

	@Id
	@SequenceGenerator(name="ABC_TPI_PARTNER_GEN", sequenceName="ABC_TPI_PARTNER_SEQ",allocationSize=10)
	@GeneratedValue(generator="ABC_TPI_PARTNER_GEN")
	@Column(name="PARTNER_ID")
	private Long id;
	
	@NotNull
	@Column(name="PARTNER_NAME",unique=true,nullable=false)
	private String partnerName;
	
	@NotNull
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY, orphanRemoval=true)
	@JoinTable(name="ABC_TPI_PARTNER_CONTACT",
			joinColumns=@JoinColumn(name="PARTNER_ID"),
			inverseJoinColumns=@JoinColumn(name="CONTACT_DETAIL_ID"))

	private Set<ContactDetail> contactDetails;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="GROUP_ID", nullable=true)
	private PartnerGroup partnerGroup;
	
	
	public void addContact(ContactDetail contact)
	{
		if (contactDetails==null)
		{
			contactDetails=new HashSet<ContactDetail>();
		}
		contactDetails.add(contact);
	}
	public Long getId() {
		return id;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public Set<ContactDetail> getContactDetails() {
		return contactDetails;
	}

	public PartnerGroup getPartnerGroup() {
		return partnerGroup;
	}

	public void setPartnerGroup(PartnerGroup partnerGroup) {
		this.partnerGroup = partnerGroup;
	}
	
	public void removeContactDetail(ContactDetail contactDetail) {
		contactDetails.remove(contactDetail);
	}
	
	public void removeContactDetails(Set<ContactDetail> contactDetails) {
		this.contactDetails.removeAll(contactDetails);
	}
	
	public static Partner newInstance(Partner partner)
	{
		if (partner==null)
		{
			return null;
		}
		
		Partner clonedPartner = new Partner();
		clonedPartner.setPartnerName(partner.getPartnerName());
		clonedPartner.setPartnerGroup(PartnerGroup.newInstance(partner.getPartnerGroup()));
		
		if (partner.getContactDetails()!=null)
		{
			for (ContactDetail cd : partner.getContactDetails())
			{
				clonedPartner.addContact(ContactDetail.newInstance(cd));
			}
			
		}
		return clonedPartner;
	}

}
