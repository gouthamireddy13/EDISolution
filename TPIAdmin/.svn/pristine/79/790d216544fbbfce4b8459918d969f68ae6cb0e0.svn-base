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
public class Role {
	
 /*   "id": 21,
    "clientId": 1,
    "clientUnitId": null,
    "name": "ABC_DEV_D",
    "rolePermissions": [
        {
            "permissionLevel": 1,
            "permissionId": "ClientRoles"
        },
        {
            "permissionLevel": 1,
            "permissionId": "DocumentReprocessB2bMessage"
        }
    ],
    "documentFilters": []*/
	
	int id;
	int clientId;
	String clientUnitId;
	String name;
	List<RolePermission> rolePermissions;
	List<String> documentFilters;
	
	
	public void setRolePermissions(List<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}
	public void setDocumentFilters(List<String> documentFilters) {
		this.documentFilters = documentFilters;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getClientUnitId() {
		return clientUnitId;
	}
	public void setClientUnitId(String clientUnitId) {
		this.clientUnitId = clientUnitId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<RolePermission> getRolePermissions() {
		return rolePermissions;
	}
	public void addRolePermissions(RolePermission rolePermission) {
		if(this.rolePermissions == null) {
			this.rolePermissions = new ArrayList();
			this.rolePermissions.add(rolePermission);
		} else {
			this.rolePermissions.add(rolePermission);
		}	
	}
	public List<String> getDocumentFilters() {
		return documentFilters;
	}
	public void addDocumentFilters(String documentFilter) {
		if(this.documentFilters == null) {
			this.documentFilters = new ArrayList();
			this.documentFilters.add(documentFilter);
		} else {
			this.documentFilters.add(documentFilter);
		}		
	}
	

}
