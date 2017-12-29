package com.abc.tpi.model.service;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.abc.tpi.model.partner.Partner;
import com.abc.tpi.model.poc.TpiManagedEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass
@AttributeOverrides
({
	@AttributeOverride(name="id",column=@Column(name="SUBSCRIPTION_ID"))
})
@Table(name="ABC_TPI_SERVICE_SUBSCRIPTION")
@JsonIgnoreProperties(ignoreUnknown=true,value={"createdDate","lastModifiedDate"})
public abstract class ServiceSubscriptionEntity extends TpiManagedEntity {

	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="PARTNER_ID", nullable=false)	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Partner partner;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="SC_ID", nullable=false)	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private ServiceCategory serviceCategory;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER, orphanRemoval=true)
	@JoinTable(name="ABC_TPI_PARTNER_SERVICE",
	joinColumns=@JoinColumn(name="SUBSCRIPTION_ID"),
	inverseJoinColumns=@JoinColumn(name="SERVICE_ID"))
	@Audited
	private Set<Service> services;
	
	@NotNull(message="Intercompany flag value is required")
	@Column(name="INTERCOMPANY_FLAG",nullable=false,columnDefinition="VARCHAR(1) default 'N'")
	private String intercompanyFlag;
	
	

	public String getIntercompanyFlag() {
		return intercompanyFlag;
	}

	public void setIntercompanyFlag(String intercompanyFlag) {
		this.intercompanyFlag = intercompanyFlag;
	}

	public void addService(Service service)
	{
		if (services==null)
		{
			services = new HashSet<Service>();
		}
		services.add(service);
	}
	
	public Partner getPartner() {
		return partner;
	}

	public Set<Service> getServices() {
		return services;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public ServiceCategory getServiceCategory() {
		return serviceCategory;
	}

	public void setServiceCategory(ServiceCategory serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

	
	
}
