package com.olx;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.olx.validation.CustomFileValidator;
import com.olx.entity.CorrectTelephones;
import com.olx.entity.IncorrectTelephones;
import com.olx.models.FileUploadModel;
import com.olx.models.TelephoneModel;



@Controller
public class MainController {

	@Autowired
	CustomFileValidator customFileValidator;
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String uploadFileFormDisplay(Model model) {
		model.addAttribute("fileUploadModel", new FileUploadModel());
		return "main";

	}
	@RequestMapping(value = "/dbanalysis")
	public ModelAndView  dbAnalysis(ModelAndView model) {
		SessionFactory factory = new Configuration().
    			configure("hibernate.cfg.xml").
    			addAnnotatedClass(CorrectTelephones.class).
    			buildSessionFactory();
		
		SessionFactory factoryInc = new Configuration().
    			configure("hibernate.cfg.xml").
    			addAnnotatedClass(IncorrectTelephones.class).
    			buildSessionFactory();
		//Getting statistics for Correct Numbers 
		Session session =factory.openSession();
		String hql = "select distinct  isfixed as Fixes, count(*) as Number from CorrectTelephones group by isfixed";
		List<?> listCorrect = session.createQuery(hql).list();
		session.close();
	
		//Getting statistics for incorrect numbers
		session =factoryInc.openSession();
		hql = "select distinct 'Incorrect Numbers' as Rejected ,count(*) as Number from IncorrectTelephones";
		List<?> listIncorrect = session.createQuery(hql).list();

		session.close();
		model = new ModelAndView("dbanalysis");
	    

	    	model.addObject("dbanalysis", listCorrect);

	    	model.addObject("dbanalysisIn", listIncorrect);

	    
	

		
		return model;
	
	}

	
    @RequestMapping(value="/savefile",method=RequestMethod.POST)  
    public ModelAndView upload(@RequestParam CommonsMultipartFile file,HttpSession session, Model model,@ModelAttribute FileUploadModel fileUploadModel, BindingResult bindingResult){  
            String path=session.getServletContext().getRealPath("/");  
            String filename=file.getOriginalFilename();  
            
        	SessionFactory factory = new Configuration().
        			configure("hibernate.cfg.xml").
        			addAnnotatedClass(CorrectTelephones.class).
        			buildSessionFactory();
        	
        	SessionFactory factoryInc = new Configuration().
        			configure("hibernate.cfg.xml").
        			addAnnotatedClass(IncorrectTelephones.class).
        			buildSessionFactory();

      
			customFileValidator.validate(fileUploadModel, bindingResult);
            if (bindingResult.hasErrors()) {
                return new ModelAndView("main");
            }
            try{  
	            byte barr[]=file.getBytes();  
		        BufferedOutputStream bout=new BufferedOutputStream(  
		                     new FileOutputStream(path+"/"+filename));  
		        bout.write(barr);  
		        bout.flush();  
		        
		        try (BufferedReader br = new BufferedReader(new FileReader(path+"/"+filename))) {
		            String line;
		            while ((line = br.readLine()) != null) {
		                String temp = line.split(",")[1];
		                CorrectTelephones tempTel = new CorrectTelephones(line.split(",")[0],line.split(",")[1],"No fixes applied");
		                if(validatePhone(temp)) {
		                	//Initialize the object
		                	insertPhone(factory,tempTel);	
		                }
		                else {
		                	tempTel = correctPhone(tempTel);
		                	 if(validatePhone(tempTel.getTelephone())) {
				                //Insert if its correct
				                insertPhone(factory,tempTel);	
				             }else {
				            	 //Create a new object
				            	 IncorrectTelephones incTempTel = new IncorrectTelephones(line.split(",")[0],line.split(",")[1]);
				            	 //Insert in the incorrect phones
				            	 insertIncPhone(factoryInc,incTempTel);
				             }
		                }
		            }
		        }
		        bout.close();  
            }catch(Exception e)
            {
            	System.out.println(e);
            }  
            //Getting statistics for Correct Numbers 
    		Session sessionC =factory.openSession();
    		String hql = "select distinct  isfixed as Fixes, count(*) as Number from CorrectTelephones group by isfixed";
    		List<?> listCorrect = sessionC.createQuery(hql).list();
    		sessionC.close();
    	
    		//Getting statistics for incorrect numbers
    		Session sessionI =factoryInc.openSession();
    		hql = "select distinct 'Incorrect Numbers' as Rejected ,count(*) as Number from IncorrectTelephones";
    		List<?> listIncorrect = sessionI.createQuery(hql).list();

    		sessionI.close();
    	    
    		ModelAndView modelDB = new ModelAndView("dbanalysis");
    	    
    	    
    		modelDB.addObject("dbanalysis", listCorrect);
    		modelDB.addObject("dbanalysisIn", listIncorrect); 
    		return modelDB;
        } 
		@RequestMapping(value = "/downloadfile", method = RequestMethod.GET)
		public ModelAndView generateExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		  
		  SessionFactory factory = new Configuration().
	    			configure("hibernate.cfg.xml").
	    			addAnnotatedClass(CorrectTelephones.class).
	    			buildSessionFactory();
		  
		  SessionFactory factoryInc = new Configuration().
	    			configure("hibernate.cfg.xml").
	    			addAnnotatedClass(IncorrectTelephones.class).
	    			buildSessionFactory();
		  Session session = factory.getCurrentSession();
		  Session sessionInc = factoryInc.getCurrentSession();

			try {
				session.beginTransaction();
				sessionInc.beginTransaction();
				@SuppressWarnings("unchecked")
				List<CorrectTelephones> cTelephones = session.createQuery("from CorrectTelephones").getResultList();
				@SuppressWarnings("unchecked")
				List<IncorrectTelephones> IncTelephones = sessionInc.createQuery("from IncorrectTelephones").getResultList();
				
				//commit transaction
				session.getTransaction().commit();
			
				
				ModelAndView modelAndView = new ModelAndView("excelView", "correcttelephones",cTelephones);
				modelAndView.addObject("incorrecttelephones", IncTelephones);
				return modelAndView;	
			}finally {
				factory.close();
				factoryInc.close();
			}


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
    	public void insertPhone(SessionFactory factory, CorrectTelephones telephone) {
    		
    		Session session1 = factory.getCurrentSession();
    		session1.beginTransaction();
			session1.save(telephone);
			try {
				session1.getTransaction().commit();
			}catch(Exception e){
				System.out.println("ID already exists");
			}
    		session1.close();
    	}
    	public void insertIncPhone(SessionFactory factory, IncorrectTelephones telephone) {
    		
    		Session session1 = factory.getCurrentSession();
    		session1.beginTransaction();
			session1.save(telephone);
			try {
				session1.getTransaction().commit();
			}catch(Exception e){
				System.out.println("ID already exists");
			}
    		session1.close();
    	}
    	public CorrectTelephones correctPhone(CorrectTelephones tempTel) {
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

    		tempTel.setIsfixed(fix);
    		tempTel.setTelephone(phoneNumber);
    		return tempTel;
    	}
    	
    	
    
    	

}
