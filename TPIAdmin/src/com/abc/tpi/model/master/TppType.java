package com.abc.tpi.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="ABC_TPI_TPP_TYPE")
public class TppType{
	@Id
	@SequenceGenerator(name="ABC_TPI_TPP_TYPE_GEN", sequenceName="ABC_TPI_TPP_TYPE_SEQ",allocationSize=1)
	@GeneratedValue(generator="ABC_TPI_TPP_TYPE_GEN")
	@Column(name="TPP_TYPE_ID")
	private Long id;

	@NotNull
	@Column(name="TPP_TYPE_CD",updatable=false,nullable=false,unique=true)
	private Short typeCode;
	
	@Column(name="TPP_TYPE_DESC")
	private String description;
	
	public String getDescription() {
		return description;
	}
	public Long getId() {
		return id;
	}
	public Short getTypeCode() {
		return typeCode;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setTypeCode(Short typeCode) {
		this.typeCode = typeCode;
	}
	
	public static TppType newInstance(TppType tppType)
	{
		if (tppType==null)
		{
			return null;
		}
		TppType clonedTppType = new TppType();
		
		clonedTppType.setDescription(tppType.getDescription());
		clonedTppType.setTypeCode(tppType.getTypeCode());
		
		return clonedTppType;
	}
}
