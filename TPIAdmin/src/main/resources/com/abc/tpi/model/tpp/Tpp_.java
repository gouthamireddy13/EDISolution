package com.abc.tpi.model.tpp;

import com.abc.tpi.model.master.Delimiter;
import com.abc.tpi.model.master.Protocol;
import com.abc.tpi.model.master.TppType;
import com.abc.tpi.model.partner.ContactDetail;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-29T14:46:13.673-0400")
@StaticMetamodel(Tpp.class)
public class Tpp_ {
	public static volatile SingularAttribute<Tpp, Long> id;
	public static volatile SingularAttribute<Tpp, TppType> type;
	public static volatile SingularAttribute<Tpp, String> name;
	public static volatile CollectionAttribute<Tpp, ContactDetail> contactDetails;
	public static volatile CollectionAttribute<Tpp, Protocol> protocols;
	public static volatile ListAttribute<Tpp, Transaction> transactions;
	public static volatile SingularAttribute<Tpp, LightWellPartner> lightWellPartner;
	public static volatile SingularAttribute<Tpp, Delimiter> segmentDelimiter;
	public static volatile SingularAttribute<Tpp, Delimiter> elementDelimiter;
	public static volatile SingularAttribute<Tpp, Delimiter> compositeElementDelimiter;
	public static volatile SingularAttribute<Tpp, Delimiter> repeatDelimiter;
}
