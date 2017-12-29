/**
 * 
 */
package com.abc.environment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ARINDAMSIKDAR
 *
 */
@Entity
@Table(name = "ABC_ENVIRONMENT_INFO")
public class EnvironmentInfo {
	
	@JsonProperty
	@Id
	@Column(name = "ID")
	private Long id;

	@JsonProperty
	@NotNull
	@Column(name = "PARAM_NAME")
	private String paramName;
	
	@JsonProperty
	@NotNull
	@Column(name = "PARAM_VALUE")
	private String paramVal;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getParamName() {
		return paramName;
	}


	public void setParamName(String paramName) {
		this.paramName = paramName;
	}


	public String getParamVal() {
		return paramVal;
	}


	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}
	
	
	
}
