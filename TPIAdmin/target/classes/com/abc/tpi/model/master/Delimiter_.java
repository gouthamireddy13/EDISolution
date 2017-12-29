package com.abc.tpi.model.master;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-29T14:46:13.563-0400")
@StaticMetamodel(Delimiter.class)
public class Delimiter_ {
	public static volatile SingularAttribute<Delimiter, Long> id;
	public static volatile SingularAttribute<Delimiter, String> delimiter;
	public static volatile SingularAttribute<Delimiter, String> description;
	public static volatile SingularAttribute<Delimiter, Boolean> isSegment;
	public static volatile SingularAttribute<Delimiter, Boolean> isElement;
	public static volatile SingularAttribute<Delimiter, Boolean> isComposite;
	public static volatile SingularAttribute<Delimiter, Boolean> isRepeat;
}
