package com.abc.tpi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.abc.dashboard.domain.SdDataLoadResult;
import com.abc.tpi.domain.SeedDataImport;
import com.abc.tpi.model.partner.ContactDetail;
import com.abc.tpi.model.partner.Partner;
import com.abc.tpi.repository.PartnerRepository;

@Controller
public class HomeController {

	@Autowired
	private PartnerRepository partnerRepsitory;

	@RequestMapping(value = "index" ,method = RequestMethod.GET)
	public String home(Model model, HttpSession session) {
		Partner partner = new Partner();
		ContactDetail contact = new ContactDetail();
		partner.getContactDetails().add(contact);

		model.addAttribute("partner", partner);
		return "home";
	}

	@RequestMapping(value = "index", method = RequestMethod.POST)
	public String createParnter(@ModelAttribute("partner") Partner partner) {
		partnerRepsitory.save(partner);
		return "redirect:index.html";

	}
	
	@RequestMapping(value="dataAdmin", method=RequestMethod.GET)
	public String dataAdmin(Model model, HttpSession session)
	{

		model.addAttribute("entityName", "");
		model.addAttribute("message","");
		model.addAttribute("seedData",new SeedDataImport());		
		return "seedData";
	}
	
	@RequestMapping(value="dataAdminSd", method=RequestMethod.GET)
	public String dataAdminSd(Model model, HttpSession session)
	{

		model.addAttribute("entityName", "");
		model.addAttribute("linesProcessed","");
		model.addAttribute("linesFailed","");
		model.addAttribute("seedDataSd",new SeedDataImport());		
		return "seedDataSd";
	}
	
	@RequestMapping(value="dataLoad", method=RequestMethod.GET)
	public String dataLoad(Model model, HttpSession session)
	{

		model.addAttribute("entityName", "");
		model.addAttribute("message","");
		model.addAttribute("loadData",new SeedDataImport());		
		return "loadData";
	}
	
	@RequestMapping(value="fileImport", method=RequestMethod.GET)
	public String fileImport(Model model, HttpSession session)
	{

		model.addAttribute("entityName", "");
		model.addAttribute("message","");
		model.addAttribute("loadData",new SeedDataImport());		
		return "fileImport";
	}
}
