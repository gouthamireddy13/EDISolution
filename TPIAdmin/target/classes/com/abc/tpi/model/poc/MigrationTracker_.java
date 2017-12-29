package com.abc.tpi.model.poc;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-29T14:46:13.610-0400")
@StaticMetamodel(MigrationTracker.class)
public class MigrationTracker_ {
	public static volatile SingularAttribute<MigrationTracker, Long> id;
	public static volatile SingularAttribute<MigrationTracker, Long> wipObjectId;
	public static volatile SingularAttribute<MigrationTracker, Long> finalObjectId;
	public static volatile SingularAttribute<MigrationTracker, Long> wipVersionNum;
	public static volatile SingularAttribute<MigrationTracker, Long> finalVersionNum;
	public static volatile SingularAttribute<MigrationTracker, String> entityClass;
	public static volatile SingularAttribute<MigrationTracker, Boolean> deleted;
	public static volatile SingularAttribute<MigrationTracker, String> comment;
}
