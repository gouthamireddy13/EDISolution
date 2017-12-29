package com.abc.tpi.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="ABC_TPI_DELIMITER")
public class Delimiter {
	
	
	@Id
	@SequenceGenerator(name="ABC_TPI_DELIMITER_GEN", sequenceName="ABC_TPI_DELIMITER_SEQ",allocationSize=1)
	@GeneratedValue(generator="ABC_TPI_DELIMITER_GEN")
	@Column(name="DELIMITER_ID")
	private Long id;
	
	@NotNull
	@Column(name="DELIMITER_CODE", length=5, nullable=false, unique=true)
	private String delimiter;
	
	@Column(name="DELIMITER_DESC")
	private String description;
	
	@Column(name="IS_SEGMENT",nullable=true)
	private boolean isSegment=false;
	
	@Column(name="IS_ELEMENT",nullable=true)
	private boolean isElement=false;
	
	@Column(name="IS_COMPOSITE",nullable=true)
	private boolean isComposite=false;
	
	@Column(name="IS_REPEAT",nullable=true)
	private boolean isRepeat=false;
	
	public String getDelimiter() {
		return delimiter;
	}

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public boolean isComposite() {
		return isComposite;
	}

	public boolean isElement() {
		return isElement;
	}

	public boolean isSegment() {
		return isSegment;
	}

	public void setComposite(boolean isComposite) {
		this.isComposite = isComposite;
	}

	public void setDelimiter(String delimeter) {
		this.delimiter = delimeter;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setElement(boolean isElement) {
		this.isElement = isElement;
	}

	public void setSegment(boolean isSegment) {
		this.isSegment = isSegment;
	}

	public boolean isRepeat() {
		return isRepeat;
	}

	public void setRepeat(boolean isRepeat) {
		this.isRepeat = isRepeat;
	}
	
	public static Delimiter newInstance(Delimiter delimiter)
	{
		if (delimiter==null)
		{
			return null;
		}
		
		Delimiter clone = new Delimiter();
		clone.setComposite(delimiter.isComposite());
		clone.setDelimiter(delimiter.getDelimiter());
		clone.setDescription(delimiter.getDescription());
		clone.setElement(delimiter.isElement());
		clone.setRepeat(delimiter.isRepeat());
		clone.setSegment(delimiter.isSegment());
		
		return clone;
	}
	
}
