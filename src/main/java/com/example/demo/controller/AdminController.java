package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Company;
import com.example.demo.entity.JobApply;
import com.example.demo.entity.JobSeekers;
import com.example.demo.entity.SelectedCandidates;
import com.example.demo.entity.Test;
import com.example.demo.entity.TestResult;
import com.example.demo.entity.Vacancy;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("/adminLogin")
	public ModelAndView adminLogin(Model model) {
		Admin admin = new Admin();
		model.addAttribute("admin", admin);
		ModelAndView view = new ModelAndView("adminLogin");
		return view;
	}

	/*
	@PostMapping("/ValidateAdminLogin")
	public ModelAndView validateAdminLogin(@ModelAttribute("admin") Admin admin, Model model) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<Admin> entity = new HttpEntity<Admin>(admin, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Boolean> response = restTemplate.exchange("http://localhost:8091/JobPathFinder/api/v1/adminLogin",
				HttpMethod.POST, entity, Boolean.class);

		boolean responseFlag = response.getBody();
		System.out.println("responseFlag =" + responseFlag);

		if (responseFlag == true) {
			ModelAndView view = new ModelAndView("adminHome");
			return view;
		} else {
			model.addAttribute("responseText", "Username/Password incorrect...");
			ModelAndView view = new ModelAndView("/adminLogin");
			return view;
		}

	}
	*/

	@GetMapping("/adminHome")
	public ModelAndView companyHome(Model model, Authentication authentication, Admin admin) {

		
		/*String username = authentication.getName();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Admin> entity = new HttpEntity<Admin>(admin, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Admin> response = restTemplate
				.getForEntity("http://localhost:8091/JobPathFinder/api/v1/adminLogin/" + username, Admin.class);

		Admin responseObject = response.getBody();

		System.out.println("responseFlag=" + responseObject);

		if (responseObject != null) { // login success

			// System.out.println("compId ==" + responseObject.getCompanyId());
			// session.setAttribute("company", responseObject);
			// Company company1 = (Company) session.getAttribute("company");

			// int compId = company1.getCompanyId();
			// model.addAttribute("companyId", compId);
			 * 
			 * 
			 */

			ModelAndView view = new ModelAndView("adminHome");

			return view;
	}
/*
		} else {
			model.addAttribute("resp", "*Email/pass doesnot exists");

			ModelAndView view = new ModelAndView("admin/companylogin");

			return view;

		}
		*/
			
			
			 @GetMapping("/viewVacanciesinadmin")
			  public ModelAndView viewVacanciess(Model model)
				{
				 // Company company = (Company) session.getAttribute("company");
				  //int cid = company.getCompanyId();

					 RestTemplate restTemplate = new RestTemplate();
					 ResponseEntity<Vacancy[]> responseEntity = restTemplate
								.getForEntity("http://localhost:8091/JobPathFinder/api/v1/allVacancies", Vacancy[].class);

					 Vacancy[] responseBody =  responseEntity.getBody(); 
					 List< Vacancy> vacancyList = Arrays.asList(responseBody);
					 
					//System.out.println("vacancyList "+vacancyList.size());  
					 model.addAttribute("vacanciesList",vacancyList);
					ModelAndView view = new ModelAndView("viewVacanciesinAdmin");
					return view;
				}
			 
			 
			 
			 
			 @GetMapping("/viewTestsinAdmin")
			  public ModelAndView viewTestsinAdmin(Model model)
				{
					 RestTemplate restTemplate = new RestTemplate();
					 ResponseEntity<Test[]> responseEntity = restTemplate
								.getForEntity("http://localhost:8091/JobPathFinder/api/v1/allTests", Test[].class);

					 Test[] responseBody =  responseEntity.getBody(); 
					 List< Test> testList = Arrays.asList(responseBody);
					System.out.println("testList "+testList.size());  
					 model.addAttribute("testListt",testList);
					ModelAndView view = new ModelAndView("viewTestsinAdmin");
					return view;
				}
			 
			 
			 
			 
			  @GetMapping("/viewJobApplyDetailsinAdmin")
			  public ModelAndView viewJobApplyDetails(Model model){
					 RestTemplate restTemplate = new RestTemplate();
					 ResponseEntity<JobApply[]> responseEntity = restTemplate
								.getForEntity("http://localhost:8091/JobPathFinder/api/v1/allJobAppliers", JobApply[].class);

					 JobApply[] responseBody =  responseEntity.getBody(); 
					 List<JobApply> jobApplyList = Arrays.asList(responseBody);
					 
					System.out.println("jobApplyList "+jobApplyList.size());  
					 model.addAttribute("jobAppliersList",jobApplyList);
					 
					 //////////////////////////////////////////////////////////////////////////////
					 ///////////////////////////
					ModelAndView view = new ModelAndView("viewJobApplyDetailsinAdmin");
					return view;
				}
			 
			  
			  @GetMapping("/viewCompaniesinadmin")
			  public ModelAndView viewCompaniess(Model model)
				{
				 // Company company = (Company) session.getAttribute("company");
				  //int cid = company.getCompanyId();

					 RestTemplate restTemplate = new RestTemplate();
					 ResponseEntity<Company[]> responseEntity = restTemplate
								.getForEntity("http://localhost:8091/JobPathFinder/api/v1/allCompanies", Company[].class);

					 Company[] responseBody =  responseEntity.getBody(); 
					 List<Company> compList = Arrays.asList(responseBody);
					 
					
					 System.out.println("pic" +compList.get(0).getLogo());
					 
					//System.out.println("vacancyList "+vacancyList.size());  
					 model.addAttribute("companyList",compList);
					ModelAndView view = new ModelAndView("viewCompaniesinadmin");
					return view;
				}
			  
			  
			 /*
			  for (Vacancy vacancy : vacancyList) {
				Company company = vacancy.getCompany();
				String imageUrl = "http://localhost:8091/JobPathFinder/api/files/download/" + company.getLogo();
				company.setLogo(imageUrl);

			}
			*/
			 
			  
			  
			  @GetMapping("/viewJobseekersinadmin")
			  public ModelAndView viewJobseekersinadmin(Model model)
				{
				 // Company company = (Company) session.getAttribute("company");
				  //int cid = company.getCompanyId();

					 RestTemplate restTemplate = new RestTemplate();
					 ResponseEntity<JobSeekers[]> responseEntity = restTemplate
								.getForEntity("http://localhost:8091/JobPathFinder/api/v1/allJobseekers", JobSeekers[].class);

					 JobSeekers[] responseBody =  responseEntity.getBody(); 
					 List<JobSeekers> jobList = Arrays.asList(responseBody);
					
					System.out.println("jobList "+jobList.size()); 
					System.out.println("name =" +jobList.get(0).getFirstName());
					 model.addAttribute("jobSeekersList",jobList);
					 
					 //////////////////////////////////////////////////////////////////////////////
					 ///////////////////////////
					ModelAndView view = new ModelAndView("viewJobseekersinadmin");
					return view;
				}
			  
			  @GetMapping("/viewTestResultsinadmin")
			  public ModelAndView viewTestResultsinadmin(Model model)
				{
				 // Company company = (Company) session.getAttribute("company");
				  //int cid = company.getCompanyId();

					 RestTemplate restTemplate = new RestTemplate();
					 ResponseEntity<TestResult[]> responseEntity = restTemplate
								.getForEntity("http://localhost:8091/JobPathFinder/api/v1/allTestResults", TestResult[].class);

					 TestResult[] responseBody =  responseEntity.getBody(); 
					 List<TestResult> testList = Arrays.asList(responseBody);
					
					System.out.println("testList "+testList.size()); 
					System.out.println("name =" +testList.get(0).getResult());
					 model.addAttribute("test",testList);
					 
					 //////////////////////////////////////////////////////////////////////////////
					 ///////////////////////////
					ModelAndView view = new ModelAndView("viewTestResultsinadmin");
					return view;
				}
			  
			  @GetMapping("/viewSelectedCandidatesinadmin")
			  public ModelAndView viewSelectedCandidatesinadmin(Model model)
				{
				 // Company company = (Company) session.getAttribute("company");
				  //int cid = company.getCompanyId();

					 RestTemplate restTemplate = new RestTemplate();
					 ResponseEntity<SelectedCandidates[]> responseEntity = restTemplate
								.getForEntity("http://localhost:8091/JobPathFinder/api/v1/allSelectedCandidates", SelectedCandidates[].class);

					 SelectedCandidates[] responseBody =  responseEntity.getBody(); 
					 List<SelectedCandidates> candList = Arrays.asList(responseBody);
					
					System.out.println("candList "+candList.size()); 
					System.out.println("score =" +candList.get(0).getScore());
					 model.addAttribute("candidates",candList);
					 
					 //////////////////////////////////////////////////////////////////////////////
					 ///////////////////////////
					ModelAndView view = new ModelAndView("viewSelectedCandidatesinadmin");
					return view;
				}
			  
			  
	}

