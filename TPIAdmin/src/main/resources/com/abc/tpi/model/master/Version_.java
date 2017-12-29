package com.abc.tpi.model.master;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-29T14:46:13.579-0400")
@StaticMetamodel(Version.class)
public class Version_ {
	public static volatile SingularAttribute<Version, Long> id;
	public static volatile SingularAttribute<Version, Integer> versionNumber;
	public static volatile SingularAttribute<Version, String> versionDescription;
}
