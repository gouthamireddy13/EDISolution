/**
 * 
 */
package com.abc.tpi.utils;

import java.util.ArrayList;

/**
 * @author ARINDAMSIKDAR
 *
 */
public class UploadDataRestResponse {
	
	String entityName;
	String message;	
	ArrayList<SeedDataInsertStatMsg> seedDataInsertStatMsgList;
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList<SeedDataInsertStatMsg> getSeedDataInsertStatMsgList() {
		return seedDataInsertStatMsgList;
	}
	public void setSeedDataInsertStatMsgList(ArrayList<SeedDataInsertStatMsg> seedDataInsertStatMsgList) {
		this.seedDataInsertStatMsgList = seedDataInsertStatMsgList;
	}
	
	

}
