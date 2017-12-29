package com.abc.dashboard.model;

import java.io.Serializable;
import java.lang.Long;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: SdServiceType
 *
 */
@Entity
@Table(name="ABC_SD_SERVICE_TYPE")

public class SdServiceType implements Serializable {

	   
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="ABC_TPI_MASTER_GEN", sequenceName="ABC_TPI_MASTER_GEN_SEQ",allocationSize=5)
	@GeneratedValue(generator="ABC_TPI_MASTER_GEN")
	private Long id;

	@NotNull(message="Name is Required")
	@Column(name="NAME",unique=true, nullable=false)
	String name;
	
	@Column(name="DESCRIPTION",unique=false, nullable=true)
	String description;
	
	public SdServiceType() {
		super();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getId() {
		return this.id;
	}
	public String getName() {
		return name;
	}   

	public void setName(String name) {
		this.name = name;
	}
   
}
