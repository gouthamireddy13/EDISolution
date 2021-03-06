/**
 * 
 */
package com.abc.dashboard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author ARINDAMSIKDAR
 *
 */
@Entity
@Table(name="ABC_BUSINESS_UNIT_LOOKUP",schema="PCCE")
@IdClass(PcceAbcBusinessUnitLookupKey.class)
public class PcceAbcBusinessUnitLookup implements Serializable  {
	
	private static final long serialVersionUID = -6398488982476253203L;

	@Id
	@Column(name="SOURCE_ID",nullable=true)
	String sourceId;
	
	@Id
	@Column(name="DESTINATION_ID",nullable=true)
	String destinationId;
	
	@Id
	@Column(name="BUSINESS_UNIT",nullable=true)
	String businessUnit;
	
	@Id
	@Column(name="SUB_BUSINESS_UNIT",nullable=true)
	String subBusinessUnit;
	
	@Id
	@Column(name="SERVICE_PREAMBLE",nullable=true)
	String servicePreamble;

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getSubBusinessUnit() {
		return subBusinessUnit;
	}

	public void setSubBusinessUnit(String subBusinessUnit) {
		this.subBusinessUnit = subBusinessUnit;
	}

	public String getServicePreamble() {
		return servicePreamble;
	}

	public void setServicePreamble(String servicePreamble) {
		this.servicePreamble = servicePreamble;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
