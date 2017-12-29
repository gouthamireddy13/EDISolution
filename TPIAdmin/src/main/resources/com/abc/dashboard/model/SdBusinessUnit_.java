package com.abc.dashboard.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-10-26T10:09:12.487-0400")
@StaticMetamodel(SdBusinessUnit.class)
public class SdBusinessUnit_ {
	public static volatile SingularAttribute<SdBusinessUnit, Long> id;
	public static volatile SingularAttribute<SdBusinessUnit, String> name;
	public static volatile SetAttribute<SdBusinessUnit, SdBusinessSubUnit> subUnits;
}
