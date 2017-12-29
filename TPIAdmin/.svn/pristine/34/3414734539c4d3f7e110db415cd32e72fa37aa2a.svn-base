package com.abc.json.wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CustomWrapper extends ObjectMapper{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomWrapper()
	{
		this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

}
