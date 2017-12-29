package com.abc.tpi.model.partner;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-29T14:46:13.595-0400")
@StaticMetamodel(Partner.class)
public class Partner_ {
	public static volatile SingularAttribute<Partner, Long> id;
	public static volatile SingularAttribute<Partner, String> partnerName;
	public static volatile CollectionAttribute<Partner, ContactDetail> contactDetails;
	public static volatile SingularAttribute<Partner, PartnerGroup> partnerGroup;
}
