package com.abc.tpi.model.master;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="mapForDropDownProjection", types={com.abc.tpi.model.master.TpiMap.class})
public interface MapForDropDownProjection {
	Long getId();
	String getMapName();	
}
