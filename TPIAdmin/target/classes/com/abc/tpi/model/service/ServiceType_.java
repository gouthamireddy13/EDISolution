package com.abc.tpi.model.service;

import com.abc.tpi.model.master.Direction;
import com.abc.tpi.model.master.Document;
import com.abc.tpi.model.master.Version;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-29T14:46:13.657-0400")
@StaticMetamodel(ServiceType.class)
public class ServiceType_ {
	public static volatile SingularAttribute<ServiceType, Long> id;
	public static volatile SingularAttribute<ServiceType, ServiceCategory> serviceCategory;
	public static volatile SingularAttribute<ServiceType, String> businessServiceName;
	public static volatile SingularAttribute<ServiceType, Direction> direction;
	public static volatile SingularAttribute<ServiceType, Document> document;
	public static volatile SingularAttribute<ServiceType, Version> version;
}
