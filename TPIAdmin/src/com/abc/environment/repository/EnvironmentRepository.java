/**
 * 
 */
package com.abc.environment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.environment.model.EnvironmentInfo;

/**
 * @author ARINDAMSIKDAR
 *
 */
@Repository("environmentRepository")
public interface EnvironmentRepository extends JpaRepository<EnvironmentInfo, Long>  {

	EnvironmentInfo findOneByParamNameIgnoreCase(String paramName);
}
