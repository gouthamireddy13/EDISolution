package com.abc.tpi.model.migrator;

import com.abc.tpi.model.service.BusinessService;

public class BusinessServiceEntityMigratorWrapper extends TpiEntityMigratorWrapper<BusinessService> {

	public BusinessServiceEntityMigratorWrapper(BusinessService entity, BusinessService parent) {
		super(entity, parent);
	}

	public BusinessServiceEntityMigratorWrapper(BusinessService entity) {
		super(entity);
	}

}
