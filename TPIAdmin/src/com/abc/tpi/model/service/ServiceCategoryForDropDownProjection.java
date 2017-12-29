package com.abc.tpi.model.service;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="serviceCategoryForDropDown", types={ServiceCategory.class})
public interface ServiceCategoryForDropDownProjection {
	//@Value("#{target.category}_#{target.gsIdProd}_#{target.isaIdProd}")
	String getName();
	Long getId();
}
