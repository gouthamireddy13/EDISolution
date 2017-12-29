package com.abc.tpi.model.poc;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-29T14:46:13.626-0400")
@StaticMetamodel(TpiManagedEntity.class)
public class TpiManagedEntity_ {
	public static volatile SingularAttribute<TpiManagedEntity, Long> id;
	public static volatile SingularAttribute<TpiManagedEntity, Long> versionNum;
	public static volatile SingularAttribute<TpiManagedEntity, Date> createdDate;
	public static volatile SingularAttribute<TpiManagedEntity, Date> lastModifiedDate;
}
