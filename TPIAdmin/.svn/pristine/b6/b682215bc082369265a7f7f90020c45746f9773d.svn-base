package com.abc.tpi.model.service;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.abc.tpi.model.tpp.LightWellPartner;

@Entity
@NamedEntityGraphs
({
	@NamedEntityGraph(name="ServiceCategory.graphLW",
			attributeNodes=
			{
				@NamedAttributeNode(value="lightWellPartners")
			}
	)
})

@Table(name="ABC_TPI_SERVICE_CATEGORY")
public class ServiceCategory {

	@Id
	@SequenceGenerator(name="ABC_TPI_SC_GEN", sequenceName="ABC_TPI_SC_SEQ",allocationSize=10)
	@GeneratedValue(generator="ABC_TPI_SC_GEN")
	@Column(name="SC_ID")
	private Long id;
	
	//@NotNull
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY, orphanRemoval=true)
	@JoinTable(name="ABC_TPI_SERVICE_CAT_LW",
			joinColumns=@JoinColumn(name="SC_ID"),
			inverseJoinColumns=@JoinColumn(name="LW_ID"))	
	private Set<LightWellPartner> lightWellPartners;
	
	@NotNull
	@Column(name="NAME",nullable=false,unique=true)
	private String name;
	
	public Long getId() {
		return id;
	}
	
	public Set<LightWellPartner> getLightWellPartners() {
		return lightWellPartners;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addLightWellPartner(LightWellPartner lw)
	{
		if (lightWellPartners==null)
		{
			lightWellPartners = new HashSet<LightWellPartner>();
		}
		lightWellPartners.add(lw);
	}
	
	public static ServiceCategory newInstance(ServiceCategory serviceCategory)
	{
		if (serviceCategory==null)
		{
			return null;
		}
		
		ServiceCategory clonedSC = new ServiceCategory();
		clonedSC.setName(serviceCategory.getName());
		
		if (serviceCategory.getLightWellPartners()!=null)
		{
			for (LightWellPartner lw: serviceCategory.getLightWellPartners())
			{
				clonedSC.addLightWellPartner(LightWellPartner.newInstance(lw));
			}
		}
		
		return clonedSC;
	}
}
