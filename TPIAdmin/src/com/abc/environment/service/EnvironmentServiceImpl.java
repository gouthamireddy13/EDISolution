/**
 * 
 */
package com.abc.environment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.environment.model.EnvironmentInfo;
import com.abc.environment.repository.EnvironmentRepository;

/**
 * @author ARINDAMSIKDAR
 *
 */
@Service("environmentService")
public class EnvironmentServiceImpl implements EnvironmentService {

	@Autowired
	private EnvironmentRepository environmentRepository;
	
	/* (non-Javadoc)
	 * @see com.abc.environment.service.EnvironmentService#saveEnvironmentInfo(com.abc.environment.model.EnvironmentInfo)
	 */
	@Override
	public EnvironmentInfo saveEnvironmentInfo(EnvironmentInfo envInfo) {
		// TODO Auto-generated method stub
		
		return environmentRepository.save(envInfo);
	}

	/* (non-Javadoc)
	 * @see com.abc.environment.service.EnvironmentService#deleteEnvironmentInfo(com.abc.environment.model.EnvironmentInfo)
	 */
	@Override
	public void deleteEnvironmentInfo(EnvironmentInfo envInfo) {
		// TODO Auto-generated method stub
		environmentRepository.delete(envInfo);
	}

	/* (non-Javadoc)
	 * @see com.abc.environment.service.EnvironmentService#findEnvironmentInfoByParamName(java.lang.String)
	 */
	@Override
	public EnvironmentInfo findEnvironmentInfoByParamName(String paramName) {
		// TODO Auto-generated method stub
		return environmentRepository.findOneByParamNameIgnoreCase(paramName);
	}

}
