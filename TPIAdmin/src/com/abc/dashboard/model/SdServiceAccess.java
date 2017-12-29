package com.abc.dashboard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="ABC_SD_SERVICE_ACCESS", uniqueConstraints={
	    @UniqueConstraint(columnNames = {"SC_DEF_ID", "SD_SERVICE_TYPE_ID", "SOURCE_ID", "DEST_ID", "SRVC_PREAMBLE"})
	})
@NamedQueries({
	
	@NamedQuery(name = "SdServiceAccess.namedFindAllServiceAccess", 
			query = "select sa from SdServiceAccess sa "),
	@NamedQuery(name = "SdServiceAccess.namedFindSdServiceAccessBySrceIdAndDestIdAndSrvcCatDefIdAndSrvcPreambleAndSdSrvcType", 
	query = "select sa from SdServiceAccess sa where ((sa.sourceId IS NULL) OR (UPPER(sa.sourceId) LIKE UPPER(:sourceId))) AND ((sa.destinationId IS NULL) OR (UPPER(sa.destinationId) LIKE UPPER(:destinationId))) AND (sa.serviceCategoryDef.id = :serviceCategoryDefId) AND (UPPER(sa.servicePreamble) LIKE UPPER(:servicePreamble)) AND (sa.serviceType.id = :serviceTypeId)")
})

@NamedEntityGraphs
({
	@NamedEntityGraph(name="SdServiceAccess.graphWebView",
			attributeNodes=
		{
				@NamedAttributeNode(value="id"),
				@NamedAttributeNode(value="serviceCategoryDef", subgraph="serviceCategoryDef"),
				@NamedAttributeNode(value="serviceType", subgraph="serviceType"),
				@NamedAttributeNode(value="sourceId"),
				@NamedAttributeNode(value="destinationId"),
				@NamedAttributeNode(value="servicePreamble")
		},
		subgraphs=
		{
				@NamedSubgraph
				(name="serviceCategoryDef",
						attributeNodes=
					{
						@NamedAttributeNode("serviceCategory")
					}
				),
				@NamedSubgraph
				(name="serviceType",
						attributeNodes=
					{
						@NamedAttributeNode("name")
					}
				)
		}
	)
})
public class SdServiceAccess implements Serializable{
	
	private static final long serialVersionUID = -6398488982476253183L;

	@Id
	@SequenceGenerator(name="ABC_SD_MASTER_DATA_GEN", sequenceName="ABC_SD_MASTER_DATA_GEN_SEQ",allocationSize=10)
	@GeneratedValue(generator="ABC_SD_MASTER_DATA_GEN")
	@Column(name="ID")
	private Long id;
	
	@NotNull(message="Service Category Definition is required")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SC_DEF_ID", nullable=false)
	SdServiceCategoryDef serviceCategoryDef;
	
	@NotNull(message="Service Type is required")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="SD_SERVICE_TYPE_ID", nullable=false)
	SdServiceType serviceType;

	@Column(name="SOURCE_ID",nullable=true)
	String sourceId;

	@Column(name="DEST_ID",nullable=true)
	String destinationId;

	@NotNull(message="Service Preamble is Required")
	@Column(name="SRVC_PREAMBLE",nullable=false)
	String servicePreamble;


	public String getDestinationId() {
		return destinationId;
	}


	public Long getId() {
		return id;
	}


	public SdServiceCategoryDef getServiceCategoryDef() {
		return serviceCategoryDef;
	}


	public String getServicePreamble() {
		return servicePreamble;
	}


	public SdServiceType getServiceType() {
		return serviceType;
	}


	public String getSourceId() {
		return sourceId;
	}


	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}


	public void setServiceCategoryDef(SdServiceCategoryDef serviceCategoryDef) {
		this.serviceCategoryDef = serviceCategoryDef;
	}
	
	public void setServicePreamble(String servicePreamble) {
		this.servicePreamble = servicePreamble;
	}	
	
	public void setServiceType(SdServiceType serviceType) {
		this.serviceType = serviceType;
	}
	
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	@Override
	public String toString() {
		return "BusinessUnit [sourceID=" + sourceId + ", destinationID=" + destinationId + ", businessUnit="
				+ ", servicePreamble=" + servicePreamble + "]";
	}
	
	
	
	
	

}
