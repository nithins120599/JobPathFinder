package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Company;
import com.example.demo.entity.JobApply;
import com.example.demo.entity.SelectedCandidates;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("company")
public class SelectedCandidatesController {
	  
	  ////////////////////////////////////////////////////////////////////////
	  /////////////////////////////////////////////////////////////////
	  /////////////////////////////////////////////////////////////////////
	  @GetMapping("/companyReports")
		public ModelAndView companyReportsViewing(Model model) {
			Company company = new Company();

			model.addAttribute("company", company);
			ModelAndView view = new ModelAndView("companyReports");

			return view;
		}
	  
	  @GetMapping("/viewSelectedCandidatesResults")
	  public ModelAndView viewJobApplyDetails(Model model,HttpSession session){
		  Company company = (Company) session.getAttribute("company");

			int cid = company.getCompanyId();

			 RestTemplate restTemplate = new RestTemplate();
			 ResponseEntity<SelectedCandidates[]> responseEntity = restTemplate
						.getForEntity("http://localhost:8091/JobPathFinder/api/v1/findByCompanyIdInSelectedCand/"+cid, SelectedCandidates[].class);

			 SelectedCandidates[] responseBody =  responseEntity.getBody(); 
			 List<SelectedCandidates> seCandList = Arrays.asList(responseBody);
			 
			System.out.println("SelectedCandidatesList "+seCandList.size());  
			 model.addAttribute("selCandList",seCandList);
			 
			 
			ModelAndView view = new ModelAndView("companyReports");
			return view;
		}

}
