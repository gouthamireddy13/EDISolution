package com.abc.tpi.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="ABC_TPI_MAP")
@NamedQueries({
	@NamedQuery(name = "TpiMap.namedFindMapByServiceTypeId", 
				query = "select m from ServiceType st JOIN st.maps m"
						+ " where st.id = :serviceTypeId order by m.mapName")}
)

public class TpiMap {

	@Id
	@SequenceGenerator(name="ABC_TPI_MAP_GEN", sequenceName="ABC_TPI_MAP_SEQ",allocationSize=10)
	@GeneratedValue(generator="ABC_TPI_MAP_GEN")	
	@Column(name="MAP_ID")
	private Long id;	
	
	@NotNull
	@Column(name="MAP_NAME",nullable=false,unique=true)
	private String mapName;
	
	@Column(name="MAP_DESC",nullable=true)
	private String mapDescription;
	

	public Long getId() {
		return id;
	}

	public String getMapDescription() {
		return mapDescription;
	}

	public String getMapName() {
		return mapName;
	}


	public void setMapDescription(String mapDescription) {
		this.mapDescription = mapDescription;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	public static TpiMap newInstance(TpiMap map)
	{
		if (map==null)
		{
			return null;
		}
		
		TpiMap clone = new TpiMap();
		
		clone.setMapDescription(map.getMapDescription());
		clone.setMapName(map.getMapName());
		
		return clone;
	}

}
