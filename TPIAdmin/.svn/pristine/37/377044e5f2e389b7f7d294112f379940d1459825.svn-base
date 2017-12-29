package com.abc.tpi.utils;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
//@Configuration
public class TPIRestMvCConfiguration extends RepositoryRestMvcConfiguration {

	private String basePath = "/api";

	@Override
	public RepositoryRestConfiguration config()
	{
		RepositoryRestConfiguration config = super.config();
		if (!this.basePath.isEmpty())
		{
			config.setBasePath(this.basePath);
		}
		return config;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
}
