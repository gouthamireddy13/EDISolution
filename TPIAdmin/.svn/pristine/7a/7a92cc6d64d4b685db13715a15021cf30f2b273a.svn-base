package com.abc.tpi.model.service;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.abc.tpi.model.master.Protocol;
import com.abc.tpi.model.master.TpiMap;
import com.abc.tpi.model.master.Version;
import com.abc.tpi.model.poc.TpiManagedEntity;
import com.abc.tpi.model.tpp.LightWellPartner;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass
@AttributeOverrides
({
	@AttributeOverride(name="id",column=@Column(name="BUSINESS_SERVICE_ID"))
})
@JsonIgnoreProperties(ignoreUnknown=true,value={"createdDate","lastModifiedDate","service"})
public abstract class BusinessServiceEntity extends TpiManagedEntity {

	@NotNull(message="Service Type is mandarory field")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="SERVICE_TYPE_ID", nullable=false)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private ServiceType serviceType;
	
	//@NotNull(message="Version is mandarory field")
	@ManyToOne
	@JoinColumn(name="VERSION_ID", nullable=true)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Version version;

	//@NotNull(message="Protocol is mandarory field")
	@ManyToOne
	@JoinColumn(name="PROTOCOL_ID", nullable=true)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Protocol protocol;
	
	@NotNull (message="LightWell is mandarory field")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="LW_ID", nullable=false)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private LightWellPartner lightWellPartner;
	
	//@NotNull
	@Column(name="ACK",nullable=true)
	@Audited
	private boolean ack=false;
	
	@Column(name="ACK_PERIOD",nullable=true)
	@Audited
	private int ackPeriod;
	
	@Column(name="SR_ID")
	@Audited
	private String srId;
	
	@Column(name="NOTES")
	@Audited
	private String notes;
	
	//@NotNull (message="Compliance is mandarory field")
	@Column(name="COMPLIANCE", nullable=true)
	@Audited
	private boolean complianceCheck;
	
	@ManyToOne
	@JoinColumn(name="MAP_ID", nullable=true)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TpiMap map;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinTable(name="ABC_TPI_SRVC_BSNS_SRVC",
	joinColumns=@JoinColumn(name="BUSINESS_SERVICE_ID",insertable=false,updatable=false),
	inverseJoinColumns=@JoinColumn(name="SERVICE_ID",insertable=false,updatable=false))
	@NotAudited
	private Service service;
	
	@Column(name="ST_ControlNum", nullable=true)
	@Audited
	private String stControlNum;
	
	@Column(name="ISA_ControlNum", nullable=true)
	@Audited
	private String isaControlNum;
	
	@Column(name="ST_AcceptorLookUpAlias", nullable=true)
	@Audited
	private String stAcceptorLookUpAlias;
		
	@Column(name="GSId_Version", nullable=true)
	@Audited
	private String gsIdVersion;
	
	public int getAckPeriod() {
		return ackPeriod;
	}

	public String getGsIdVersion() {
		return gsIdVersion;
	}

	public String getIsaControlNum() {
		return isaControlNum;
	}

	public LightWellPartner getLightWellPartner() {
		return lightWellPartner;
	}

	public TpiMap getMap() {
		return map;
	}

	public String getNotes() {
		return notes;
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public Service getService() {
		return service;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public String getSrId() {
		return srId;
	}

	public String getStAcceptorLookUpAlias() {
		return stAcceptorLookUpAlias;
	}

	public String getStControlNum() {
		return stControlNum;
	}

	public Version getVersion() {
		return version;
	}

	public boolean isAck() {
		return ack;
	}

	public boolean isComplianceCheck() {
		return complianceCheck;
	}

	public void setAck(boolean ack) {
		this.ack = ack;
	}

	public void setAckPeriod(int ackPeriod) {
		this.ackPeriod = ackPeriod;
	}

	public void setComplianceCheck(boolean complianceCheck) {
		this.complianceCheck = complianceCheck;
	}

	public void setGsIdVersion(String gsIdVersion) {
		this.gsIdVersion = gsIdVersion;
	}

	public void setIsaControlNum(String isaControlNum) {
		this.isaControlNum = isaControlNum;
	}

	public void setLightWellPartner(LightWellPartner lightWellPartner) {
		this.lightWellPartner = lightWellPartner;
	}

	public void setMap(TpiMap map) {
		this.map = map;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public void setSrId(String srId) {
		this.srId = srId;
	}

	public void setStAcceptorLookUpAlias(String stAcceptorLookUpAlias) {
		this.stAcceptorLookUpAlias = stAcceptorLookUpAlias;
	}

	public void setStControlNum(String stControlNum) {
		this.stControlNum = stControlNum;
	}

	public void setVersion(Version version) {
		this.version = version;
	}
		

}
