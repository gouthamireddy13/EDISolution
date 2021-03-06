package com.abc.tpi.model.tpp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.abc.tpi.model.master.Direction;
import com.abc.tpi.model.master.Document;
import com.abc.tpi.model.master.Version;

@Entity
@Table(name = "ABC_TPI_TPP_TRANSACTION")
public class Transaction {
	@Id
	@SequenceGenerator(name="ABC_TPI_TPP_TRANSACTION_GEN", sequenceName="ABC_TPI_TPP_TRANSACTION_SEQ",allocationSize=10)
	@GeneratedValue(generator="ABC_TPI_TPP_TRANSACTION_GEN")
	@Column(name = "TRANSACTION_ID")
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="DIRECTION_ID", nullable=false)
	private Direction direction;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="DOC_ID", nullable=false)
	private Document document;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="VERSION_ID", nullable=false)
	private Version version;
	
	public Direction getDirection() {
		return direction;
	}
	public Document getDocument() {
		return document;
	}
	public Long getId() {
		return id;
	}
	public Version getVersion() {
		return version;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public void setVersion(Version version) {
		this.version = version;
	}
	
	public static Transaction newInstance(Transaction transaction)
	{
		if (transaction==null)
		{
			return transaction;
		}
		Transaction clone = new Transaction();
		clone.setDirection(Direction.newInstance(transaction.getDirection()));
		clone.setDocument(Document.newInstance(transaction.getDocument()));
		clone.setVersion(Version.newInstance(transaction.getVersion()));
		return clone;
		
	}
}
