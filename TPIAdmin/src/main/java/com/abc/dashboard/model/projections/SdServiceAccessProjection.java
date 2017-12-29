package com.abc.dashboard.model.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="sdServiceAccessProjection", types={SdServiceAccessProjection.class})

public interface SdServiceAccessProjection {
	
	Long getId();
	
	@Value("#{target.getServiceCategoryDef().getServiceCategory().getName()}")	
	String getServiceCategory();
	
	@Value("#{target.getServiceType().getName()}")
	String getServiceType();
	
	@Value("#{target.getServiceCategoryDef().getBusinessUnit().getName()}")	
	String getBusinessUnit();
	
	@Value("#{target.getServiceCategoryDef().getBusinessSubUnit().getSubUnitName()}")	
	String getBusinessSubUnit();
	
	String getSourceId();
	String getDestinationId();	
	String getServicePreamble();

}
