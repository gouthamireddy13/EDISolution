package com.abc.tpi.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UtilController {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private LocalContainerEntityManagerFactoryBean entityManagerFactory;
	
	@RequestMapping(value = { "/getConnectionInfo" }, method = { RequestMethod.GET })
	public ResponseEntity getConnectionInfo(HttpServletRequest request, HttpServletResponse response) {

		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		
		Logger logger = LogManager.getLogger(PartnerController.class);
		logger.debug("Invoked UtilController.getConnectionInfo()");
		
		try
		{
			result = entityManagerFactory.getDataSource().getConnection().toString();
			logger.debug(result);
			
			String jsonString = mapper.writeValueAsString(result);
			return ResponseEntity.status(HttpStatus.OK).body(jsonString);	
		}
		
		catch (Exception ex)
		{
			logger.error(ex);
			String errorString = ex.getMessage();
			try
			{
				errorString = mapper.writeValueAsString(errorString);
			}
			
			catch(Exception ex2)
			{
				logger.error(ex2);
			}
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorString);
		}
		
	}
}
