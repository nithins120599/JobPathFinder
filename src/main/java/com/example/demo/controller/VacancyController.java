package com.example.demo.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Company;
import com.example.demo.entity.Test;
import com.example.demo.entity.Vacancy;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("company")
public class VacancyController {
	 @GetMapping("/addVacancy")
		public ModelAndView AddVecency(Model model, HttpSession session) {
			Vacancy vacancy = new Vacancy();
			model.addAttribute("vacancy", vacancy);

			Company company = (Company) session.getAttribute("company");

			int cid = company.getCompanyId();
			System.out.println("companyId:::" + cid);
			model.addAttribute("companyId", company.getCompanyId());

			ModelAndView view = new ModelAndView("addVacancy");
			return view;
		}
	  
	  @PostMapping("/addingVacancy")
	  public ModelAndView addingVacancy(@ModelAttribute("vacancy") Vacancy vacancy, Model model, HttpSession session) {
	      // Get companyId from session or wherever it's stored
	      int companyId = ((Company) session.getAttribute("company")).getCompanyId();
	      
	      // Check if the Company object in the Vacancy is null, if so, initialize it
	      if (vacancy.getCompany() == null) {
	          vacancy.setCompany(new Company());
	      }
	      
	      // Set companyId in the Vacancy object's company attribute
	      vacancy.getCompany().setCompanyId(companyId);
	      
	      HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<Vacancy> entity = new HttpEntity<Vacancy>(vacancy, headers);
	      
	      RestTemplate restTemplate = new RestTemplate();
	      String url = "http://localhost:8091/JobPathFinder/api/v1/addVacancy";
	      
	      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	      String responseString = response.getBody();
	      
	      // Clearing the vacancy object after submission
	      vacancy = new Vacancy();
	      model.addAttribute("vacancy", vacancy);
	      
	      ModelAndView view = new ModelAndView("addVacancy");
	      model.addAttribute("responseString", responseString);
	      model.addAttribute("resp","Vacancies Added Successfully!!");
	      return view;
	  }

	  @GetMapping("/viewVacancies")
	  public ModelAndView viewVacancies(Model model,HttpSession session)
		{
		  Company company = (Company) session.getAttribute("company");
		  int cid = company.getCompanyId();

			 RestTemplate restTemplate = new RestTemplate();
			 ResponseEntity<Vacancy[]> responseEntity = restTemplate
						.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getVacancies/"+cid, Vacancy[].class);

			 Vacancy[] responseBody =  responseEntity.getBody(); 
			 List< Vacancy> vacancyList = Arrays.asList(responseBody);
			 
			System.out.println("vacancyList "+vacancyList.size());  
			 model.addAttribute("vacanciesList",vacancyList);
			ModelAndView view = new ModelAndView("viewVacancies");
			return view;
		}
	  
	  @GetMapping("/editVacancy/{vacancyId}")
		public ModelAndView getvacancy(@PathVariable("vacancyId") int vacancyId,Model model,HttpSession session) {
			Company company = (Company) session.getAttribute("company");
			int cid = company.getCompanyId();
			System.out.println("cid ?? ==" +cid);
			
			RestTemplate restTemplate = new RestTemplate();	
			ResponseEntity<Vacancy> responseEntity = restTemplate.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getVacancy/" + vacancyId,
					Vacancy.class);
			Vacancy vacancy = responseEntity.getBody();
			System.out.println("JOBTITLE:" + vacancy.getJobTitle());
			model.addAttribute("vac", vacancy);
			ModelAndView view = new ModelAndView("editVacancy");
			return view;
		}
	  
	  @PostMapping("/updateVacancy")
		public String updatevacancy(@ModelAttribute("vacancy") Vacancy vacancy, Model model, HttpSession session) {
			Company company = (Company) session.getAttribute("company");
			vacancy.setCompany(company);
			int cid = company.getCompanyId();
			System.out.println("companyId:::" + cid);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<Vacancy> entity = new HttpEntity<Vacancy>(vacancy, headers);

			RestTemplate restTemplate = new RestTemplate();

			String url = "http://localhost:8091/JobPathFinder/api/v1/updateVacancy/"+vacancy.getVacancyId();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

			String responseString = response.getBody();
			System.out.println("responseString11" + responseString);

			return "redirect:/company/viewVacancies";		
		}
	  
	  
	  @GetMapping("/deletevacancy/{vacancyId}")
		public String deletevacancy(@PathVariable("vacancyId") int vacancyId) {
		
			Map<String, Integer> pathVar = new HashMap<>();
			pathVar.put("vacancyId", vacancyId);
			
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.delete("http://localhost:8091/JobPathFinder/api/v1/deleteVacancy/{vacancyId}",pathVar);
			//ModelAndView view = new ModelAndView("viewProducts");
			//return view;
			return "redirect:/company/viewVacancies";
			
		}

		
}	

