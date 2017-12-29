/**
 * 
 */
package com.abc.tpi.model.migrator;

import com.abc.tpi.model.service.ServiceSubscription;

/**
 * @author a061313
 *
 */
public class ServiceSubscriptionEntityMigratorWrapper extends TpiEntityMigratorWrapper<ServiceSubscription> {

	public ServiceSubscriptionEntityMigratorWrapper(ServiceSubscription entity) {
		super(entity);
	}
	public ServiceSubscriptionEntityMigratorWrapper(ServiceSubscription entity, ServiceSubscription parent) {
		super(entity, parent);
	}

}
