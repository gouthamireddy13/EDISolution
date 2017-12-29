package com.abc.tpi.model.poc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ABC_TPI_MIGRATION")
public class MigrationTracker {
	
	@Id
	@SequenceGenerator(name="ABC_TPI_MIGRATION_GEN", sequenceName="ABC_TPI_MIGRATION_SEQ",allocationSize=10)
	@GeneratedValue(generator="ABC_TPI_MIGRATION_GEN")
	@Column(name="ID")
	private Long id;
	
	@Column(name="SOURCE_OBJ_ID", nullable = false)
	private long wipObjectId;
	
	@Column(name="DEST_OBJ_ID")
	private long finalObjectId;
	
	@Column(name="SOURCE_VRSN_NUM", nullable=false)
	private long wipVersionNum;
	
	@Column(name="DEST_VRSN_NUM")
	private long finalVersionNum;
	
	@Column(name="ENTITY_CLASS_NAME", nullable=false)
	private String entityClass;

	@Column(name="DELETED")
	private boolean deleted = false;
	
	@Column(name="COMMENT")
	private String comment;
	
	public String getComment() {
		return comment;
	}

	public String getEntityClass() {
		return entityClass;
	}

	public long getFinalObjectId() {
		return finalObjectId;
	}

	public long getFinalVersionNum() {
		return finalVersionNum;
	}

	public Long getId() {
		return id;
	}

	public long getWipObjectId() {
		return wipObjectId;
	}

	public long getWipVersionNum() {
		return wipVersionNum;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}

	public void setFinalObjectId(long finalObjectId) {
		this.finalObjectId = finalObjectId;
	}

	public void setFinalVersionNum(long finalVersionNum) {
		this.finalVersionNum = finalVersionNum;
	}

	public void setWipObjectId(long wipObjectId) {
		this.wipObjectId = wipObjectId;
	}

	public void setWipVersionNum(long wipVersionNum) {
		this.wipVersionNum = wipVersionNum;
	}

}
