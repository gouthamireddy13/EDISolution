package com.abc.dashboard.model.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="sdServiceCategoryDefProjection", types={SdServiceCategoryDefProjection.class})

public interface SdServiceCategoryDefProjection {
	Long getId();
	@Value("#{target.getServiceCategory().getName()}")
	String getServiceCategory();
	int getCategoryID();
	String getNotes();
	String getPartnerSubscription();
	@Value("#{target.getBusinessUnit().getName()}")
	String getBusinessUnitName();
	@Value("#{target.getBusinessSubUnit().getSubUnitName()}")
	String getBusinessSubUnit();
}
