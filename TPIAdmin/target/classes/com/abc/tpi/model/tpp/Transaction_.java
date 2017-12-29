package com.abc.tpi.model.tpp;

import com.abc.tpi.model.master.Direction;
import com.abc.tpi.model.master.Document;
import com.abc.tpi.model.master.Version;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-29T14:46:13.673-0400")
@StaticMetamodel(Transaction.class)
public class Transaction_ {
	public static volatile SingularAttribute<Transaction, Long> id;
	public static volatile SingularAttribute<Transaction, Direction> direction;
	public static volatile SingularAttribute<Transaction, Document> document;
	public static volatile SingularAttribute<Transaction, Version> version;
}
