package com.abc.tpi.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ABC_TPI_ACK")

public class Ack{
	@Id
	@SequenceGenerator(name="ABC_TPI_ACK_GEN", sequenceName="ABC_TPI_ACK_SEQ",allocationSize=10)
	@GeneratedValue(generator="ABC_TPI_ACK_GEN")
	@Column(name="ACK_ID")
	private Long id;
	
	@Column(name="ACK_CODE")	
	private String ackValue;
	
	@Column(name="ACK_DESC")
	private String description;
	
	public String getAckValue() {
		return ackValue;
	}
	public String getDescription() {
		return description;
	}
	public Long getId() {
		return id;
	}
	
	public void setAckValue(String ackValue) {
		this.ackValue = ackValue;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
