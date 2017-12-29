/**
 * 
 */
package com.abc.user.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ARINDAMSIKDAR
 *
 */
public class User {

	List<String> errors;
	boolean successful;
	Data data;
	String updatedObject;
	String apiVersion;
	
	
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void getErrors(String error) {
		if(errors == null) {
			errors = new ArrayList<String>();
		}
		errors.add(error);
	}
	public boolean isSuccessful() {
		return successful;
	}
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public String getUpdatedObject() {
		return updatedObject;
	}
	public void setUpdatedObject(String updatedObject) {
		this.updatedObject = updatedObject;
	}
	public String getApiVersion() {
		return apiVersion;
	}
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	
	
	
}
