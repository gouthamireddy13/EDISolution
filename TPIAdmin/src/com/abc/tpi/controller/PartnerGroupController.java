package com.abc.tpi.controller;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.abc.tpi.model.partner.PartnerGroup;
import com.abc.tpi.service.PartnerGroupService;

@Controller
public class PartnerGroupController {

	@Autowired
	PartnerGroupService partnerGroupService;

	@RequestMapping(value = { "/getPartnerGroupNames" }, method = { RequestMethod.GET })
	public ResponseEntity getPartnerGroupNames() {

		List<String> result = null;
		try {
			result = partnerGroupService.getDistinctPartnerGroupNamesForDropDown();
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());

		}
	}

	@RequestMapping(value = { "/partnerSubGroups" }, method = { RequestMethod.GET })
	public ResponseEntity getPartnerSubGroupsByName(HttpServletRequest request, HttpServletResponse response) {

		String groupName="";
		try
		{
			groupName= URLDecoder.decode(request.getParameter("groupName"), "UTF-8");
		}
		catch (Exception ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Partner Group Name is invalid");
		}
		
		List<PartnerGroup> result = null;
		
		if (groupName != null) {

			try {
				result = partnerGroupService.getPartnerGroupsByGroupNameForDropDown(groupName);
				return ResponseEntity.status(HttpStatus.OK).body(result);
			} catch (Exception ex) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to retrieve Partner Groups");
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Partner Group Name is invalid");
		}	
	}
}
