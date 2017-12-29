/**
 * 
 */
package com.abc.environment.service;

import org.springframework.stereotype.Service;

import com.abc.environment.model.EnvironmentInfo;

/**
 * @author ARINDAMSIKDAR
 *
 */
@Service
public interface EnvironmentService {

	
	EnvironmentInfo saveEnvironmentInfo(EnvironmentInfo envInfo);
	void deleteEnvironmentInfo(EnvironmentInfo envInfo);
	EnvironmentInfo findEnvironmentInfoByParamName(String paramName);
}
