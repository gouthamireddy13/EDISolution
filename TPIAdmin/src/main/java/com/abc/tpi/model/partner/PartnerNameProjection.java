package com.abc.tpi.model.partner;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="partnerName", types={Partner.class})
public interface PartnerNameProjection {
	Long getId();
	String getPartnerName();
}
