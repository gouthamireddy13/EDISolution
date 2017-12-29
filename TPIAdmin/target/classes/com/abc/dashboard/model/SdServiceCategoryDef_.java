package com.abc.dashboard.model;

import com.abc.tpi.model.service.ServiceCategory;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-10-26T10:09:12.507-0400")
@StaticMetamodel(SdServiceCategoryDef.class)
public class SdServiceCategoryDef_ {
	public static volatile SingularAttribute<SdServiceCategoryDef, Long> id;
	public static volatile SingularAttribute<SdServiceCategoryDef, ServiceCategory> serviceCategory;
	public static volatile SingularAttribute<SdServiceCategoryDef, String> categoryID;
	public static volatile SingularAttribute<SdServiceCategoryDef, SdBusinessUnit> businessUnit;
	public static volatile SingularAttribute<SdServiceCategoryDef, SdBusinessSubUnit> businessSubUnit;
	public static volatile SingularAttribute<SdServiceCategoryDef, SdYesNo> partnerSubscription;
	public static volatile SingularAttribute<SdServiceCategoryDef, String> notes;
}
