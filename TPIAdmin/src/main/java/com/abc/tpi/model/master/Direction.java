package com.abc.tpi.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ABC_TPI_DIRECTION")
public class Direction {
	
	@Id	
	@SequenceGenerator(name="ABC_TPI_DIRECTION_GEN", sequenceName="ABC_TPI_DIRECTION_SEQ",allocationSize=10)
	@GeneratedValue(generator="ABC_TPI_DIRECTION_GEN")
	@Column(name="DIRECTION_ID")
	private Long id;
	
	@Column(name="DIRECTION_CD", nullable=false, unique=true)
	private String directionCode;
	
	@Column(name="DIRECTION_DESC", nullable=true)
	private String directionDescription;

	public String getDirectionCode() {
		return directionCode;
	}

	public String getDirectionDescription() {
		return directionDescription;
	}

	public Long getId() {
		return id;
	}

	public void setDirectionCode(String directionCode) {
		this.directionCode = directionCode;
	}

	public void setDirectionDescription(String directionDescription) {
		this.directionDescription = directionDescription;
	}
	
	public static Direction newInstance(Direction direction)
	{
		if (direction==null)
		{
			return null;
		}
		Direction clone = new Direction();
		clone.setDirectionCode(direction.getDirectionCode());
		clone.setDirectionDescription(direction.getDirectionDescription());		
		return clone;
	}
	
}
