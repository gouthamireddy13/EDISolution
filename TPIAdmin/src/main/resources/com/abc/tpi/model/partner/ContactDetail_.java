package com.abc.tpi.model.partner;

import com.abc.tpi.model.master.Document;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-29T14:46:13.595-0400")
@StaticMetamodel(ContactDetail.class)
public class ContactDetail_ {
	public static volatile SingularAttribute<ContactDetail, Long> id;
	public static volatile SingularAttribute<ContactDetail, String> contactName;
	public static volatile SingularAttribute<ContactDetail, String> contactTitle;
	public static volatile SingularAttribute<ContactDetail, String> businessPhone;
	public static volatile SingularAttribute<ContactDetail, String> businessPhoneCountry;
	public static volatile SingularAttribute<ContactDetail, String> mobilePhone;
	public static volatile SingularAttribute<ContactDetail, String> mobilePhoneCountry;
	public static volatile SingularAttribute<ContactDetail, String> personalPhoneCountry;
	public static volatile SingularAttribute<ContactDetail, String> personalPhone;
	public static volatile SingularAttribute<ContactDetail, String> contactEmail;
	public static volatile SingularAttribute<ContactDetail, String> contactEmail2;
	public static volatile SingularAttribute<ContactDetail, String> businessPhoneExt;
	public static volatile SingularAttribute<ContactDetail, String> mobilePhoneExt;
	public static volatile SingularAttribute<ContactDetail, String> personalPhoneExt;
	public static volatile SingularAttribute<ContactDetail, Document> transactionType;
}
