package com.abc.dashboard.model;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;

import com.abc.tpi.model.service.ServiceCategory;
@Entity
@Table(name="ABC_SD_SRVC_CAT_DEF")


@NamedQueries({
	
	@NamedQuery(name = "SdServiceCategoryDef.namedFindServiceCategoryByName", 
			query = "select scDef from SdServiceCategoryDef scDef join scDef.serviceCategory sc "
			+ "	where UPPER(sc.name) like UPPER(:name) order by sc.name"),
	@NamedQuery(name="SdServiceCategoryDef.namedFindServiceCategoriesWithPartnerSubscription", 
				query = "select scDef from SdServiceCategoryDef scDef join scDef.serviceCategory sc "
			    + "	where scDef.partnerSubscription='Y' order by sc.name"),
	@NamedQuery(name = "SdServiceCategoryDef.namedFindServiceCategoryByCategoryId", 
		query = "select scDef from SdServiceCategoryDef scDef  "
				+ "	where scDef.categoryID =:categoryID"),
})


@NamedEntityGraphs
({
	@NamedEntityGraph(name="SdServiceCategoryDef.graphListAll",
			attributeNodes=
		{
				@NamedAttributeNode(value="serviceCategory"),
				@NamedAttributeNode(value="categoryID"),
				@NamedAttributeNode(value="partnerSubscription"),
				@NamedAttributeNode(value="businessUnit",subgraph="businessUnit"),
		},
		subgraphs=
		{
				@NamedSubgraph
				(name="businessUnit",
						attributeNodes=
					{
						@NamedAttributeNode("name")
					}
				)
		}
	)
})

public class SdServiceCategoryDef {
	
	@Id
	@SequenceGenerator(name="ABC_SD_MASTER_DATA_GEN", sequenceName="ABC_SD_MASTER_DATA_GEN_SEQ",allocationSize=10)
	@GeneratedValue(generator="ABC_SD_MASTER_DATA_GEN")
	@Column(name="ID")
	private Long id;
	
	@NotNull(message="Serveric Category Name is required")
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="SC_ID",nullable=false,unique=true)
	private ServiceCategory serviceCategory;
	
	@NotNull(message="Category Id is required")
	@Column(name="CATEGORY_ID",nullable=false,unique=true)
	private int categoryID;
	
	@NotNull(message="Business Unit is required")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="BU_ID", nullable=false)
	private SdBusinessUnit businessUnit;
	
	@NotNull(message="Business Sub Unit is required")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="BSU_ID", nullable=false)
	private SdBusinessSubUnit businessSubUnit;

	@NotNull(message="Partner Subscription is required")
	@Enumerated(EnumType.STRING)
	@Column(name="PARTNER_SUBSCRIPTION", nullable=false)
	private SdYesNo partnerSubscription;

	@Column(name="NOTES")
	private String notes;

	public SdBusinessSubUnit getBusinessSubUnit() {
		return businessSubUnit;
	}

	public SdBusinessUnit getBusinessUnit() {
		return businessUnit;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public Long getId() {
		return id;
	}

	public String getNotes() {
		return notes;
	}

	public SdYesNo getPartnerSubscription() {
		return partnerSubscription;
	}

	public ServiceCategory getServiceCategory() {
		return serviceCategory;
	}

	public void setBusinessSubUnit(SdBusinessSubUnit businessSubUnit) {
		this.businessSubUnit = businessSubUnit;
	}

	public void setBusinessUnit(SdBusinessUnit businessUnit) {
		this.businessUnit = businessUnit;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public void setPartnerSubscription(SdYesNo partnerSubscription) {
		this.partnerSubscription = partnerSubscription;
	}
	
	public void setServiceCategory(ServiceCategory serviceCategory) {
		this.serviceCategory = serviceCategory;
	}


}
