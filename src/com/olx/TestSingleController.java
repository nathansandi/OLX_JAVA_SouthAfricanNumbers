package com.olx;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.olx.models.TelephoneModel;
@Controller
public class TestSingleController {
	@RequestMapping(value = "/test")
	public String testSingle(Model model) {
		model.addAttribute("telephoneModel", new TelephoneModel());
		return "test_single";

	}
	
	@RequestMapping(value = "/validateform", method = RequestMethod.POST)
	public String processTest(Model model, @ModelAttribute("telephoneModel") TelephoneModel telephoneModel,
			BindingResult theBindingResult) {
		 if(validatePhone(telephoneModel.getTelephone())) {
         	//Return Valid
			 telephoneModel.setStatus("Result: "+telephoneModel.getTelephone()+" is valid.");
         }
         else {
        	 TelephoneModel tempTel = correctPhone(telephoneModel);
         	 if(!validatePhone(tempTel.getTelephone())) {
	                //Insert if its correct
         		    telephoneModel.setStatus("Result: "+telephoneModel.getTelephone()+" is invalid");
	          }else {
	        	     telephoneModel.setStatus("Result: "+telephoneModel.getTelephone()+" is valid, but has fixes: "+telephoneModel.getStatus());
	          }
         }
		return "test_result";

	}
	public TelephoneModel correctPhone(TelephoneModel tempTel) {
		//First situation - Telephones without 27 that is right
		String phoneNumber = tempTel.getTelephone();
		String fix = "";

		if(phoneNumber.startsWith("72") || phoneNumber.startsWith("73") || phoneNumber.startsWith("74") ||
				 phoneNumber.startsWith("82") || phoneNumber.startsWith("83") || phoneNumber.startsWith("84")) {
			 phoneNumber = "27"+phoneNumber;
			 fix = "Fix 1.Country code added. ";
		}
		//Second Situation - Telephones with extra digits
		if(phoneNumber.length()  > 11) {
			 phoneNumber = phoneNumber.substring(0,11);
			 fix = fix + "Fix 2.Extra Digits Deleted. ";
		}

		tempTel.setStatus(fix);
		tempTel.setTelephone(phoneNumber);
		return tempTel;
	}    	
	public boolean validatePhone(String phoneNumber) {
		//Check the size qq1                                                
		if(phoneNumber.startsWith("27") && (phoneNumber.length() ==11) && phoneNumber.matches("\\d+")) {
			//return true; 
			phoneNumber = phoneNumber.replace("27", "");
			if(phoneNumber.startsWith("72") || phoneNumber.startsWith("73") || phoneNumber.startsWith("74") ||
				 phoneNumber.startsWith("82") || phoneNumber.startsWith("83") || phoneNumber.startsWith("84")) {
				return true;
			}
		}  
		return false;
	}
}
