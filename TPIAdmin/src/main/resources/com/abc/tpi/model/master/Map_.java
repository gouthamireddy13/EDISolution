package com.abc.tpi.model.master;

import com.abc.tpi.domain.core.Company;
import com.abc.tpi.domain.core.PartnerType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-29T14:46:13.563-0400")
@StaticMetamodel(Map.class)
public class Map_ {
	public static volatile SingularAttribute<Map, Long> id;
	public static volatile SingularAttribute<Map, Company> company;
	public static volatile SingularAttribute<Map, PartnerType> partnerType;
	public static volatile SingularAttribute<Map, Document> document;
	public static volatile SingularAttribute<Map, Direction> direction;
	public static volatile SingularAttribute<Map, String> mapName;
	public static volatile SingularAttribute<Map, String> mapDescription;
}
