package com.abc.tpi.model.migrator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="ABC_TPI_OBJECT_TRACKER",uniqueConstraints={@UniqueConstraint(columnNames={"SOURCE_ID","CLASS_NAME"}),@UniqueConstraint(columnNames={"SOURCE_ID","DEST_ID","CLASS_NAME"})})
public class ObjectTracker {

	@Id
	@SequenceGenerator(name="ABC_TPI_TRACKER_GEN", sequenceName="ABC_TPI_TRACKER_SEQ",allocationSize=10)
	@GeneratedValue(generator="ABC_TPI_TRACKER_GEN")
	private Long id;
	
	@NotNull(message="SOURCE_ID is required")
	@Column(name="SOURCE_ID", nullable=false, updatable=false)
	private long sourceId;
	
	@Column(name="SOURCE_VRSN_NUM")
	private int sourceVersionNum;
	
	@Column(name="DEST_ID", updatable=false, nullable=true,unique=false)	
	private long destId;
	
	@Column(name="DEST_VRSN_NUM")
	private int destVersionNum;
	
	@Column(name="MIGRATED_ON")
	@Temporal(TemporalType.TIMESTAMP)
	private Date migratedOn;
	
	@Column(name="PARENT_ID")
	private long parentId;
	
	@Column(name="PARENT_VRSN_NUM")
	private int parentVersionNum;
	
	@Column(name="CLASS_NAME")
	private String className;
	
	@Column(name="PARENT_CLASS_NAME")
	private String parentClassName;
	
	@Column(name="CHANGE_CD", nullable=true)
	@Enumerated(EnumType.STRING)
	private ChangeType modificationStatus;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinTable(name="ABC_TPI_OBJECT_TRCKR_W",
			joinColumns=@JoinColumn(name="ID"))	
	private Collection<ObjectTracker> objectTrackers;
	
	@Transient
	boolean toBeExported = false;
	
	public void addObjectTracker(ObjectTracker objectTracker) {
		if (this.objectTrackers==null)
		{
			this.objectTrackers = new ArrayList<ObjectTracker>();
		}
		this.objectTrackers.add(objectTracker);
	}
	public String getClassName() {
		return className;
	}
	public long getDestId() {
		return destId;
	}
	public int getDestVersionNum() {
		return destVersionNum;
	}
	public Long getId() {
		return id;
	}
	public Date getMigratedOn() {
		return migratedOn;
	}
	public ChangeType getModificationStatus() {
		return modificationStatus;
	}
	public Collection<ObjectTracker> getObjectTrackers() {
		return objectTrackers;
	}
	public String getParentClassName() {
		return parentClassName;
	}
	public long getParentId() {
		return parentId;
	}
	public int getParentVersionNum() {
		return parentVersionNum;
	}
	public long getSourceId() {
		return sourceId;
	}
	public int getSourceVersionNum() {
		return sourceVersionNum;
	}
	public boolean isToBeExported() {
		return toBeExported;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public void setDestId(long destId) {
		this.destId = destId;
	}
	public void setDestVersionNum(int destVersionNum) {
		this.destVersionNum = destVersionNum;
	}
	public void setMigratedOn(Date migratedOn) {
		this.migratedOn = migratedOn;
	}
	public void setModificationStatus(ChangeType modificationStatus) {
		this.modificationStatus = modificationStatus;
	}
	public void setParentClassName(String parentClassName) {
		this.parentClassName = parentClassName;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public void setParentVersionNum(int parentVersionNum) {
		this.parentVersionNum = parentVersionNum;
	}
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	public void setSourceVersionNum(int sourceVersionNum) {
		this.sourceVersionNum = sourceVersionNum;
	}
	public void setToBeExported(boolean toBeExported) {
		this.toBeExported = toBeExported;
	}
}
