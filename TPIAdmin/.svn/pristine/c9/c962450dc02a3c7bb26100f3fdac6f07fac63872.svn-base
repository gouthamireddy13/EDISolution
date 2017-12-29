package com.abc.dashboard.model;

import java.io.Serializable;
import java.lang.Long;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: SdBusinessUnit
 *
 */
@Entity
@Table(name="ABC_SD_BSNS_UNIT")

@NamedQueries({
	
	@NamedQuery(name = "SdBusinessUnit.namedFindSubUnitsByUnitName", 
			query = "select su from SdBusinessUnit bu join bu.subUnits su "
					+ "	where bu.name=:name order by su.subUnitName"),
	@NamedQuery(name = "SdBusinessUnit.namedFindByUnitNameAndSubUnitName", 
			query = "select bu from SdBusinessUnit bu join bu.subUnits su "
					+ "	where bu.name=:name and su.subUnitName = :subUnitName"),
	@NamedQuery(name = "SdBusinessUnit.namedFindSubUnitForABCid",
			query = "select sub.subUnitName from "
					+ "	SdServiceCategoryDef sdScDef join sdScDef.businessSubUnit sub"
					+ " join sdScDef.serviceCategory sc join sc.lightWellPartners lw "
					+ "	where lw.productionGsId=:gsId"),
	@NamedQuery(name = "SdBusinessUnit.namedFindSubUnitForLwId",
			query = "select sub.subUnitName from "
					+ "	SdServiceCategoryDef sdScDef join sdScDef.businessSubUnit sub"
					+ " join sdScDef.serviceCategory sc join sc.lightWellPartners lw "
					+ "	where lw.id=:id")
})
public class SdBusinessUnit implements Serializable {

	   
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="ABC_TPI_MASTER_GEN", sequenceName="ABC_TPI_MASTER_GEN_SEQ",allocationSize=5)
	@GeneratedValue(generator="ABC_TPI_MASTER_GEN")
	private Long id;

	@NotNull
	@Column(name="NAME", unique=true, nullable=false)
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY, orphanRemoval=true)
	@JoinTable(name="ABC_SD_BSNS_UNIT_SUBUNIT",
			joinColumns=@JoinColumn(name="SBU_ID"),
			inverseJoinColumns=@JoinColumn(name="ID"))
	private Set<SdBusinessSubUnit> subUnits;
	
	public SdBusinessUnit() {
		super();
	}   
	public void addSubUnit(SdBusinessSubUnit subUnit)
	{
		if (subUnit!=null)
		{
			if (subUnits == null)
			{
				subUnits = new HashSet<SdBusinessSubUnit>();
			}
			
			if (!subUnits.contains(subUnit))
			{
				subUnits.add(subUnit);
			}
		}
	}
	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return name;
	}
	public Set<SdBusinessSubUnit> getSubUnits() {
		return subUnits;
	}
	public void setName(String name) {
		this.name = name;
	}
   
}
