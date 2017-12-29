package com.abc.dashboard.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-10-26T10:09:12.495-0400")
@StaticMetamodel(SdServiceAccess.class)
public class SdServiceAccess_ {
	public static volatile SingularAttribute<SdServiceAccess, Long> id;
	public static volatile SingularAttribute<SdServiceAccess, SdServiceCategoryDef> serviceCategoryDef;
	public static volatile SingularAttribute<SdServiceAccess, SdServiceType> serviceType;
	public static volatile SingularAttribute<SdServiceAccess, String> sourceId;
	public static volatile SingularAttribute<SdServiceAccess, String> destinationId;
	public static volatile SingularAttribute<SdServiceAccess, String> servicePreamble;
}
