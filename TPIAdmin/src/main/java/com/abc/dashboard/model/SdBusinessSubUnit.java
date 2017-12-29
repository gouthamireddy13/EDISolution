package com.abc.dashboard.model;

import java.io.Serializable;
import java.lang.Long;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: BusinessSubUnit
 *
 */
@Entity
@Table(name="ABC_SD_BSNS_SUB_UNT")

public class SdBusinessSubUnit implements Serializable {

	   
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="ABC_TPI_MASTER_GEN", sequenceName="ABC_TPI_MASTER_GEN_SEQ",allocationSize=5)
	@GeneratedValue(generator="ABC_TPI_MASTER_GEN")
	private Long id;

	@NotNull(message="Subunit Name value is required")
	@Column(name="NAME",nullable=false,unique=true)
	private String subUnitName;
	
	public SdBusinessSubUnit() {
		super();
	}
	public Long getId() {
		return this.id;
	}
	public String getSubUnitName() {
		return subUnitName;
	}   
	public void setId(Long id) {
		this.id = id;
	}

	public void setSubUnitName(String subUnitName) {
		this.subUnitName = subUnitName;
	}
   
}
