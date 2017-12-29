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
public class Data {
	
/*    "id": 325,
    "clientId": 1,
    "username": "v423706",
    "email": "nagagouthami.b@hcl.com",
    "useLdap": true,
    "givenName": "Naga",
    "familyName": "Reddy",
    "phone": null,
    "language": 1,
    "displayTimezone": "America/New_York",
    "enabled": true,*/
    
    int id;
	int clientId;
	String username;
	String email;
	boolean useLdap;
	String givenName;
	String familyName;
	String phone;
	int language;
	String displayTimezone;
	boolean enabled;
	List<Role> roles;
	List<UserPermission> userPermissions;
	List<String> userSearchPrefs;
	List<String> userDisplayPrefs;
	List<String> documentFilters;
	List<String> userSearch;
	List<Permission> permissions;
	
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public void setUserPermissions(List<UserPermission> userPermissions) {
		this.userPermissions = userPermissions;
	}
	public void setUserSearchPrefs(List<String> userSearchPrefs) {
		this.userSearchPrefs = userSearchPrefs;
	}
	public void setUserDisplayPrefs(List<String> userDisplayPrefs) {
		this.userDisplayPrefs = userDisplayPrefs;
	}
	public void setDocumentFilters(List<String> documentFilters) {
		this.documentFilters = documentFilters;
	}
	public void setUserSearch(List<String> userSearch) {
		this.userSearch = userSearch;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isUseLdap() {
		return useLdap;
	}
	public void setUseLdap(boolean useLdap) {
		this.useLdap = useLdap;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getLanguage() {
		return language;
	}
	public void setLanguage(int language) {
		this.language = language;
	}
	public String getDisplayTimezone() {
		return displayTimezone;
	}
	public void setDisplayTimezone(String displayTimezone) {
		this.displayTimezone = displayTimezone;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void addRoles(Role role) {
		if(roles == null) {
			roles = new ArrayList<Role>();
			roles.add(role);
		} else {
			roles.add(role);
		}		
	}
	public List<UserPermission> getUserPermissions() {
		return userPermissions;
	}
	public void addUserPermissions(UserPermission userPermission) {
		if(userPermissions == null) {
			userPermissions = new ArrayList<UserPermission>();
		} 		
		userPermissions.add(userPermission);
	}
	public List<String> getUserSearchPrefs() {
		return userSearchPrefs;
	}
	public void addUserSearchPrefs(String userSearchPref) {
		if(userSearchPrefs == null) {
			userSearchPrefs = new ArrayList<String>();
		}
		userSearchPrefs.add(userSearchPref);
	}
	public List<String> getUserDisplayPrefs() {
		return userDisplayPrefs;
	}
	public void addUserDisplayPrefs(String userDisplayPref) {
		if(userDisplayPrefs == null) {
			userDisplayPrefs = new ArrayList<String>();
		}
		userDisplayPrefs.add(userDisplayPref);
	}
	public List<String> getDocumentFilters() {
		return documentFilters;
	}
	public void addDocumentFilters(String documentFilter) {
		if(documentFilters == null) {
			documentFilters = new ArrayList<String>();
		}
		documentFilters.add(documentFilter);
	}
	public List<String> getUserSearch() {
		return userSearch;
	}
	public void addUserSearch(String search) {
		if(userSearch == null) {
			userSearch = new ArrayList<String>();
		}
		userSearch.add(search);
	}
	public List<Permission> getPermissions() {
		return permissions;
	}
	public void addPermissions(Permission permission) {
		if(permissions == null) {
			permissions = new ArrayList<Permission>();
		}
		permissions.add(permission);
	}
	
	
}
