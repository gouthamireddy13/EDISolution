package com.abc.tpi.model.service;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="serviceTypeProjectionIdName", types={ServiceType.class})
public interface ServiceTypeProjectionIdName {
	Long getId();
	String getBusinessServiceName();
}
