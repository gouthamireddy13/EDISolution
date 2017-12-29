package com.abc.tpi.model.migrator;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-29T14:46:13.595-0400")
@StaticMetamodel(ObjectTracker.class)
public class ObjectTracker_ {
	public static volatile SingularAttribute<ObjectTracker, Long> sourceId;
	public static volatile SingularAttribute<ObjectTracker, Integer> sourceVersionNum;
	public static volatile SingularAttribute<ObjectTracker, Long> destId;
	public static volatile SingularAttribute<ObjectTracker, Integer> destVersionNum;
	public static volatile SingularAttribute<ObjectTracker, Date> migratedOn;
}
