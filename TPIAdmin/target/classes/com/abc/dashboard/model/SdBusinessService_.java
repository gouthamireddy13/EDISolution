package com.abc.dashboard.model;

import com.abc.tpi.model.master.Direction;
import com.abc.tpi.model.master.Document;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-10-26T10:09:12.359-0400")
@StaticMetamodel(SdBusinessService.class)
public class SdBusinessService_ {
	public static volatile SingularAttribute<SdBusinessService, Long> id;
	public static volatile SingularAttribute<SdBusinessService, String> serviceKey;
	public static volatile SingularAttribute<SdBusinessService, SdServiceCategoryDef> serviceCategory;
	public static volatile SingularAttribute<SdBusinessService, SdServiceType> serviceType;
	public static volatile SingularAttribute<SdBusinessService, String> interCoSendToBU;
	public static volatile SingularAttribute<SdBusinessService, Direction> direction;
	public static volatile SingularAttribute<SdBusinessService, Document> document;
	public static volatile SingularAttribute<SdBusinessService, String> servicePreamble;
	public static volatile SingularAttribute<SdBusinessService, String> serviceDescription;
	public static volatile SingularAttribute<SdBusinessService, String> serviceLevel;
	public static volatile SingularAttribute<SdBusinessService, String> serviceUserId;
	public static volatile SingularAttribute<SdBusinessService, String> serviceLevelId;
}
