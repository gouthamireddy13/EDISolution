package com.abc.tpi.model.poc;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.abc.tpi.model.migrator.ChangeType;

@MappedSuperclass
public abstract class TpiManagedEntity {

	@Id
	@GeneratedValue(generator="ABC_TPI_ID_GEN")
	@SequenceGenerator(name="ABC_TPI_ID_GEN", sequenceName="ABC_TPI_ID_SEQ",allocationSize=10)
	private Long id;
	
	@Version
	@Column(name="VERSION_NUM")
	private int versionNum;
		
	@CreatedDate
	@Column(name="CREATED", updatable=false)
	private Date createdDate;
	
	@Column(name="LASTMODIFIED", updatable=true)
	@LastModifiedDate
	private Date lastModifiedDate;
	
	@Column(name="CHANGE_TYPE")
	@Enumerated(EnumType.STRING)
	private ChangeType changeType;

	public ChangeType getChangeType() {
		return changeType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	
	public Long getId() {
		return id;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public int getVersionNum() {
		return versionNum;
	}

	public void setChangeType(ChangeType changeType) {
		this.changeType = changeType;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}

}
