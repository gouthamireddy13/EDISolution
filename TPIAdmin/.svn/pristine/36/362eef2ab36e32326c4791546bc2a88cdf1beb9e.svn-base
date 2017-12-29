package com.abc.dashboard.model;

import com.abc.dashboard.model.SdServiceCategoryDef;
import com.abc.dashboard.model.SdServiceType;
import com.abc.tpi.model.master.Direction;
import com.abc.tpi.model.master.Document;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: SdBusinessService
 *
 */
@Entity

@NamedQueries({
	
	@NamedQuery(name = "SdBusinessService.namedFindAllBusinessServices", 
			query = "select bs from SdBusinessService bs"),
	@NamedQuery(name="SdBusinessService.namedFindSdBusinessServiceById", 
				query = "select bs from SdBusinessService bs where bs.id=:id"),
	@NamedQuery(name="SdBusinessService.namedFindSdBusinessServiceByServiceKey",
				query = "select bs from SdBusinessService bs where UPPER(bs.serviceKey) LIKE UPPER(:serviceKey)")
})


@NamedEntityGraphs
({
	@NamedEntityGraph(name="SdBusinessService.graphWebView",
			attributeNodes=
		{
				@NamedAttributeNode(value="id"),
				@NamedAttributeNode(value="serviceCategory"),
				@NamedAttributeNode(value="serviceType",subgraph="serviceType"),
				@NamedAttributeNode(value="interCoSendToBU"),
				@NamedAttributeNode(value="direction", subgraph="direction"),
				@NamedAttributeNode(value="document",subgraph="document"),
				@NamedAttributeNode(value="servicePreamble"),
				@NamedAttributeNode(value="serviceKey"),
				@NamedAttributeNode(value="serviceDescription"),
				@NamedAttributeNode(value="serviceLevel"),
				@NamedAttributeNode(value="serviceUserId")
				
		},
		subgraphs=
		{
				@NamedSubgraph
				(name="serviceType",
						attributeNodes=
					{
						@NamedAttributeNode("name")
					}
				),
				@NamedSubgraph
				(name="direction",
						attributeNodes=
					{
						@NamedAttributeNode("directionDescription")
					}
				),
				@NamedSubgraph
				(name="document",
						attributeNodes=
					{
						@NamedAttributeNode("documentType")
					}
				)				
		}
	)
})

@Table(name="ABC_SD_BSNS_SERVICE")
public class SdBusinessService implements Serializable {

	private static final long serialVersionUID = 1L;
 
	@Id
	@SequenceGenerator(name="ABC_TPI_MASTER_GEN", sequenceName="ABC_TPI_MASTER_GEN_SEQ",allocationSize=5)
	@GeneratedValue(generator="ABC_TPI_MASTER_GEN")
	@Column(name="ID")
	private Long id;

	@NotNull(message="Service Key is Required")
	@Column(name="SERVCE_KEY",nullable=false,unique=true,updatable=false)
	private String serviceKey;
	
	
	@NotNull(message="Service Category Def is required")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SC_DEF_ID", nullable=false)
	private SdServiceCategoryDef serviceCategory;
	
	@NotNull(message="Service Type is required")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="STYPE_ID", nullable=false)
	private SdServiceType serviceType;

	@Column(name="INTER_CO_SND_TO_BU",nullable=true)
	private String interCoSendToBU;
	
	@NotNull(message="Direction is required")
	@ManyToOne
	@JoinColumn(name="DIRECTION_ID", nullable=false)
	private Direction direction;
	
	@NotNull(message="Transaction is required")
	@ManyToOne
	@JoinColumn(name="DOC_ID", nullable=false)
	private Document document;
	
	@NotNull(message="Service Preamble is required")
	@Column(name="SERVCE_PREAMBLE",nullable=false)
	private String servicePreamble;
	
	@Column(name="SERVCE_DESCRIPTION")
	private String serviceDescription;
	
	@Column(name="SERVCE_LVL")
	private String serviceLevel;
	
	@Column(name="SERVCE_USR_ID")
	private String serviceUserId;	

	@Column(name="SERVICE_LVL_ID")
	private String serviceLevelId;
	
	public SdBusinessService() {
		super();
	}
	public Direction getDirection() {
		return this.direction;
	}
	public Document getDocument() {
		return this.document;
	}
	public Long getId() {
		return this.id;
	}
	public String getInterCoSendToBU() {
		return this.interCoSendToBU;
	}   
	public SdServiceCategoryDef getServiceCategory() {
		return this.serviceCategory;
	}

	public String getServiceKey() {
		return this.serviceKey;
	}

	public String getServiceLevel() {
		return this.serviceLevel;
	}

	public String getServiceLevelId() {
		return serviceLevelId;
	}   
	public String getServicePreamble() {
		return this.servicePreamble;
	}

	public String getServiceDescription() {
		return this.serviceDescription;
	}   
	public SdServiceType getServiceType() {
		return this.serviceType;
	}

	public String getServiceUserId() {
		return this.serviceUserId;
	}   
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void setDocument(Document document) {
		this.document = document;
	}   
	public void setInterCoSendToBU(String interCoSendToBU) {
		this.interCoSendToBU = interCoSendToBU;
	}

	public void setServiceCategory(SdServiceCategoryDef serviceCategory) {
		this.serviceCategory = serviceCategory;
	}   

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}   
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public void setServiceLevelId(String serviceLevelId) {
		this.serviceLevelId = serviceLevelId;
	}   
	public void setServicePreamble(String servicePreamble) {
		this.servicePreamble = servicePreamble;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}   
	public void setServiceType(SdServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public void setServiceUserId(String serviceUserId) {
		this.serviceUserId = serviceUserId;
	}
   
}
