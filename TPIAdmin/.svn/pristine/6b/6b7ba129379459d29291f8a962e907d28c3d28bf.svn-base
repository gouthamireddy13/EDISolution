package com.abc.tpi.model.tpp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="tppListViewProjection", types = {Tpp.class})
public interface TppListViewProjection 
{
	Long getId();
	String getName();
	@Value("#{target.getType().getDescription()}")
	String getType();
}
