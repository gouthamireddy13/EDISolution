package com.abc.tpi.model.service;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.abc.tpi.model.master.Direction;
import com.abc.tpi.model.master.Document;
import com.abc.tpi.model.master.TpiMap;

@Entity
@NamedQueries
(
	{
	@NamedQuery(name="ServiceType.namedFindAllServiceTypesByServiceCategoryId", 
			query="select distinct m from ServiceType m where m.serviceCategory.id = :serviceCategoryId"),
	@NamedQuery(name="ServiceType.namedFindServiceTypesForServiceCategoryAndTransaction", 
			query="select distinct m from ServiceType m "
					+ "	where m.serviceCategory.id = :serviceCategoryId "
					+ "	and direction.id = :directionId "
					+ "	and document.id=:documentId "
					+ ""),
	@NamedQuery(name="ServiceType.namedFindServiceTypesForServiceCategoryAndTransactionDocumentAll", 
			query="select distinct m from ServiceType m "
			+ "	where m.serviceCategory.id = :serviceCategoryId "
			+ "	and direction.id = :directionId "			
			+ ""),
	@NamedQuery(name="ServiceType.namedFindServiceTypeByNameCompanyPartnerCategory", 
			query="select distinct m from ServiceType m where m.businessServiceName = :businessServiceName and m.company= :company and m.partnerCategory= :partnerCategory"),
	@NamedQuery(name="ServiceType.namedFindMapForServiceTypeById",
				query="select m from ServiceType s join s.maps m where s.id =:id and m.id =:mapId")
	
	}
)

@Table(name="ABC_TPI_SERVICE_TYPE",uniqueConstraints=@UniqueConstraint(columnNames={"SERVICE_NAME", "COMPANY","PARTNER_CATEGORY"}))
public class ServiceType {
	@Id
	@SequenceGenerator(name="ABC_TPI_SERVICE_TYPE_GEN", sequenceName="ABC_TPI_SERVICE_TYPE_SEQ",allocationSize=10)
	@GeneratedValue(generator="ABC_TPI_SERVICE_TYPE_GEN")
	@Column(name="SERVICE_TYPE_ID")
	private Long id;
	
	@NotNull(message="Service Category value is required")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="SC_ID", nullable=false)
	private ServiceCategory serviceCategory;
	
	@NotNull(message="Service Name value is required")
	@Column(name="SERVICE_NAME",nullable=false)
	private String businessServiceName;
	
	@NotNull(message="Direction value is required")
	@ManyToOne
	@JoinColumn(name="DIRECTION_ID", nullable=false)
	private Direction direction;
	
	@NotNull(message="Document value is required")
	@ManyToOne
	@JoinColumn(name="DOC_ID", nullable=false)
	private Document document;
	
/*	@NotNull(message="Version value is required")
	@ManyToOne
	@JoinColumn(name="VERSION_ID", nullable=false)
	private Version version;
	*/
	@NotNull(message="COMPANY value is required")
	@Enumerated(EnumType.STRING)
	@Column(name="COMPANY", nullable=false)
	private CompanyEnum company;
	
	//@NotNull(message="PartnerCategory is required")	
	@Enumerated(EnumType.STRING)
	@Column(name="PARTNER_CATEGORY", nullable=true)
	private PartnerCategoryEnum partnerCategory;
	
	@Column(name="MAP")
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="ABC_TPI_SRVC_TYPE_MAP",
			joinColumns=@JoinColumn(name="SERVICE_TYPE_ID"),
			inverseJoinColumns=@JoinColumn(name="MAP_ID"))
	private Set<TpiMap> maps;
	

	public String getBusinessServiceName() {
		return businessServiceName;
	}
	public CompanyEnum getCompany() {
		return company;
	}
	public Direction getDirection() {
		return direction;
	}
	public Document getDocument() {
		return document;
	}
	public Long getId() {
		return id;
	}
	public Set<TpiMap> getMaps() {
		return maps;
	}
	public PartnerCategoryEnum getPartnerCategory() {
		return partnerCategory;
	}
	public ServiceCategory getServiceCategory() {
		return serviceCategory;
	}

	public void setBusinessServiceName(String businessServiceName) {
		this.businessServiceName = businessServiceName;
	}
	public void setCompany(CompanyEnum company) {
		this.company = company;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public void setPartnerCategory(PartnerCategoryEnum partnerCategory) {
		this.partnerCategory = partnerCategory;
	}
	public void setServiceCategory(ServiceCategory serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

	
	public void addMap(TpiMap map)
	{
		if (this.maps==null)
		{
			this.maps = new HashSet<TpiMap>();
		}
		this.maps.add(map);
	}
	
	public static ServiceType newInstance(ServiceType serviceType)
	{
		if (serviceType==null)
		{
			return null;
		}
		
		ServiceType clone = new ServiceType();
		
		clone.setBusinessServiceName(serviceType.getBusinessServiceName());
		clone.setCompany(serviceType.getCompany());
		clone.setDirection(Direction.newInstance(serviceType.getDirection()));
		clone.setDocument(Document.newInstance(serviceType.getDocument()));

		for (TpiMap map: serviceType.getMaps())
		{
			clone.addMap(TpiMap.newInstance(map));
		}
		
		clone.setPartnerCategory(serviceType.getPartnerCategory());
		clone.setServiceCategory(ServiceCategory.newInstance(serviceType.getServiceCategory()));
		
		return clone;
	}
}
