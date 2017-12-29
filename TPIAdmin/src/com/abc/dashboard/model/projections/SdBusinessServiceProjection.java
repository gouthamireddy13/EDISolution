package com.abc.dashboard.model.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.abc.dashboard.model.SdBusinessService;

@Projection(name="sdBusinessService", types={SdBusinessService.class})

public interface SdBusinessServiceProjection {
	Long getId();
	
	@Value("#{target.getServiceCategory().getServiceCategory().getName()}")
	String getServiceCategory();
	
	@Value("#{target.getServiceType().getName()}")
	String getServiceType();
	
	@Value("#{target.getServiceCategory().getBusinessUnit().getName()}")
	String getBusinessUnitName();
	@Value("#{target.getServiceCategory().getBusinessSubUnit().getSubUnitName()}")
	String getBusinessSubUnit();
	
	String getInterCoSendToBU();
	
	@Value("#{target.getDirection().getDirectionCode()}")
	String getDirectionCode();

	@Value("#{target.getDocument().getDocumentType()}")
	String getTransactionCode();
	
	String getServicePreamble();
	String getServiceKey();
	String getServiceLevel();
	String gserviceUserId();
}
