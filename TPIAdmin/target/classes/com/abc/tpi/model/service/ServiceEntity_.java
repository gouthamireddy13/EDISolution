package com.abc.tpi.model.service;

import com.abc.tpi.model.master.Delimiter;
import com.abc.tpi.model.poc.TpiManagedEntity_;
import com.abc.tpi.model.tpp.LightWellPartner;
import com.abc.tpi.model.tpp.Tpp;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-06-29T14:46:13.641-0400")
@StaticMetamodel(ServiceEntity.class)
public class ServiceEntity_ extends TpiManagedEntity_ {
	public static volatile SingularAttribute<ServiceEntity, Tpp> tpp;
	public static volatile SingularAttribute<ServiceEntity, LightWellPartner> lightWellPartner;
	public static volatile SingularAttribute<ServiceEntity, Delimiter> segmentDelimiter;
	public static volatile SingularAttribute<ServiceEntity, Delimiter> elementDelimiter;
	public static volatile SingularAttribute<ServiceEntity, Delimiter> compositeElementDelimiter;
	public static volatile SingularAttribute<ServiceEntity, Delimiter> repeatDelimiter;
	public static volatile CollectionAttribute<ServiceEntity, BusinessService> businessServices;
	public static volatile SingularAttribute<ServiceEntity, String> srId;
	public static volatile SingularAttribute<ServiceEntity, String> notes;
}
