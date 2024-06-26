package com.example.demo.controller;

import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Company;
import com.example.demo.entity.Documents;
import com.example.demo.entity.Email;
import com.example.demo.entity.JobApply;
import com.example.demo.entity.JobSeekers;
import com.example.demo.entity.Questions;
import com.example.demo.entity.SelectedCandidates;
import com.example.demo.entity.Test;
import com.example.demo.entity.Users;
import com.example.demo.entity.Vacancy;
import com.example.demo.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("company")
public class CompanyController {
	@Autowired 
	UsersService userService;
	
	@GetMapping("/companyHome")
	public ModelAndView companyHome(Model model, HttpSession session, Authentication authentication, Company company) {

		String username = authentication.getName();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Company> entity = new HttpEntity<Company>(company, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Company> response = restTemplate
				.getForEntity("http://localhost:8091/JobPathFinder/api/v1/companyLogin/" + username, Company.class);

		Company responseObject = response.getBody();

		System.out.println("responseFlag=" + responseObject);

		if (responseObject != null) { // login success

			System.out.println("compId ==" + responseObject.getCompanyId());
			session.setAttribute("company", responseObject);
			Company company1 = (Company) session.getAttribute("company");

			int compId = company1.getCompanyId();
			model.addAttribute("companyId", compId);

			ModelAndView view = new ModelAndView("companyHome");

			return view;

		} else {
			model.addAttribute("resp", "*Email/pass doesnot exists");

			ModelAndView view = new ModelAndView("companylogin");

			return view;

		}
	}

	@GetMapping("/companyLogin")
	public ModelAndView complog(Model model) {
		Company company = new Company();

		model.addAttribute("company", company);
		ModelAndView view = new ModelAndView("company/companylogin");

		return view;
	}

	/*
	 * @PostMapping("/ValidateCompanyLogin") public ModelAndView
	 * ValidateCompanyLogin(@ModelAttribute("Company") Company company, HttpSession
	 * session, Model model) { HttpHeaders headers = new HttpHeaders();
	 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	 * 
	 * HttpEntity<Company> entity = new HttpEntity<Company>(company, headers);
	 * 
	 * RestTemplate restTemplate = new RestTemplate();
	 * 
	 * ResponseEntity<Company> response =
	 * restTemplate.exchange("http://localhost:8091/JobPathFinder/api/v1/companyValidation",
	 * HttpMethod.POST, entity, Company.class);
	 * 
	 * Company responseObject = response.getBody();
	 * 
	 * System.out.println("responseFlag=" + responseObject);
	 * 
	 * if (responseObject != null) { // login success
	 * 
	 * session.setAttribute("company", responseObject); ModelAndView view = new
	 * ModelAndView("company/companyhome");
	 * 
	 * return view;
	 * 
	 * } else { model.addAttribute("resp", "*Email/pass doesnot exists");
	 * 
	 * ModelAndView view = new ModelAndView("company/companylogin");
	 * 
	 * return view;
	 * 
	 * } }
	 */

	@GetMapping("/getProfile")
	public ModelAndView getProfile(Model model, HttpSession session) {
		Company company = (Company) session.getAttribute("company");

		int cid = company.getCompanyId();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Company> responseEntity = restTemplate
				.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getCompany/" + cid, Company.class);
		Company company1 = responseEntity.getBody();
		String imageUrl = company1.getLogo();
		company1.setLogo(imageUrl);
		System.out.println("company = " + company1.getCompanyName());
		model.addAttribute("company", company1);
		ModelAndView view = new ModelAndView("companyProfile");

		return view;

	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*

	@GetMapping("/editCompProf")
	public ModelAndView editCompProf(Model model) {
		Company company = new Company();

		model.addAttribute("company", company);
		ModelAndView view = new ModelAndView("editCompanyProfile");

		return view;
	}
	
	/*
	
	/*

	@PostMapping("/editPicture")
	public String editPicture(@RequestParam("logo1") MultipartFile logo1, Model model,HttpSession session) {
		
		try {
			Company company1 = (Company) session.getAttribute("company");

			int cid = company1.getCompanyId();
			
			// Create the upload directory if not exists
	        Files.createDirectories(Path.of("./temp_uploads"));
	        
	        
			byte[] picBytes = logo1.getBytes();
			String companyPicName = "temp_" + System.currentTimeMillis() + "_" + logo1.getOriginalFilename();
			Files.write(Paths.get("./temp_uploads", companyPicName), picBytes);

			// Set the product picture name in the product object
			company1.setLogo(companyPicName);
           
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Company> entity = new HttpEntity<Company>(company1, headers);
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8091/JobPathFinder/api/v1/addCompany";
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			String responseString = response.getBody();
			// System.out.println("responseFlag=" + responseFlag);

			// Up to here product is adding

			//////////////////////// code to upload product picture
			HttpHeaders picHeaders = new HttpHeaders();
			picHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
			MultiValueMap<String, Object> picMap = new LinkedMultiValueMap<>();
			picMap.add("file", new FileSystemResource("./temp_uploads/" + companyPicName));
			picMap.add("filename",companyPicName);
			HttpEntity<MultiValueMap<String, Object>> picEntity = new HttpEntity<>(picMap, picHeaders);

			// Send a request to upload the product picture
			String uploadPicUrl = "http://localhost:8091/JobPathFinder/api/files/upload";
			ResponseEntity<String> picResponse = restTemplate.exchange(uploadPicUrl, HttpMethod.POST, picEntity,
					String.class);

			String picResponseString = picResponse.getBody();

			// Clean up: Delete the temporary product picture
			Files.deleteIfExists(Paths.get("./temp_uploads", companyPicName));

			///////////////////////
           /*
           
			ModelAndView view = new ModelAndView("/getProfile");
			model.addAttribute("responseString", responseString);
			
			*/

	/*
			 return "redirect:/company/getProfile";
		} catch (IOException e) {
			// Handle exceptions related to file operations or HTTP requests
			// Handle exceptions related to file operations or HTTP requests
	        model.addAttribute("responseString", e.getMessage());
	        // Redirect to some error page or handle it accordingly
	        return "errorPage";
		}

	}


       */
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////
	////////////////////////company side viewing results
	/////////////////////////////////////////////////////
	
	@GetMapping("/companyResult")
	public ModelAndView companyResultview(Model model) {
		Company company = new Company();

		model.addAttribute("company", company);
		ModelAndView view = new ModelAndView("companyResults");

		return view;
	}
	

	  @GetMapping("/viewCompanyResults")
	  public ModelAndView viewCompanyResults(Model model,HttpSession session){
		  
	  
		 Company c1 = (Company) session.getAttribute("company");
		 System.out.println("c1 (company object)==" +c1);
		  int companyId = c1.getCompanyId();

			 RestTemplate restTemplate = new RestTemplate();
			 ResponseEntity<JobApply[]> responseEntity = restTemplate
						.getForEntity("http://localhost:8091/JobPathFinder/api/v1/findByCompanyIdInJobApply/"+companyId,JobApply[].class);

			 JobApply[] responseBody =  responseEntity.getBody(); 
			 List<JobApply> jobApplyList = Arrays.asList(responseBody);
			 
			 for(JobApply jobb :jobApplyList) {
				int userid= jobb.getJobseeker().getUserId();
				System.out.println("useriddddddd =" +userid);
				 RestTemplate restTemplate1 = new RestTemplate();
				 
				 ResponseEntity<Documents[]> responseEntity1 = restTemplate
							.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getDocuments/"+userid,Documents[].class);
				 Documents[] responseBody1 =  responseEntity1.getBody(); 
				 List<Documents> documList = Arrays.asList(responseBody1);
				System.out.println("Document List By USERID sizee =" +documList.size());
				 model.addAttribute("documentsList",documList);
			 }
			System.out.println("jobApplyList "+jobApplyList.size());  
			System.out.println("jobApplyList "+jobApplyList);  
			 model.addAttribute("jobAppliersList",jobApplyList);
			 	 
			 //////////////////////////////////////////////////////////////////////////////
			 ///////////////////////////
			JobApply joo = new JobApply();
			model.addAttribute("jobapply",joo);
			ModelAndView view = new ModelAndView("companyResults");
			return view;
		}
	  
	  
	  @PostMapping("/selectedCandRes")
	  public String viewCompanyResults(@ModelAttribute("jobapply") JobApply  jobAp,HttpSession session){
		  
		
		 int applyIddd =jobAp.getApplyId();
		 System.out.println("id =" +applyIddd);
		 RestTemplate restTemplate = new RestTemplate();
		 ResponseEntity<JobApply> responseEntity = restTemplate
					.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getJobApply/"+applyIddd,JobApply.class);
		 
		 JobApply jobAppDetails =  responseEntity.getBody(); 
		 //List<JobApply> jobApplyList = Arrays.asList(responseBody);
		 
		System.out.println("responseBody (JobApply)==" +jobAppDetails);
		// model.addAttribute("jobAppliersList",jobApplyList);
		
		
		int userId=jobAppDetails.getJobseeker().getUserId();
		System.out.println("userId" +userId);
		
		int vacancyIdd=jobAppDetails.getVacancy().getVacancyId();
		System.out.println("VacancyId" +vacancyIdd);
		
		 ResponseEntity<Test> responseEntity1 = restTemplate
					.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getTestByVacancyIdd/"+vacancyIdd,Test.class);
		 
		 Test testDetails =  responseEntity1.getBody(); 
		 System.out.println("TestDet==" +testDetails);
		 
		 SelectedCandidates selCand=new SelectedCandidates();
		 selCand.setJobSeeker(jobAppDetails.getJobseeker());
		 selCand.setVacancy(jobAppDetails.getVacancy());
		 selCand.setTest(testDetails);
		 selCand.setScore(Integer.parseInt(jobAppDetails.getFinalScore()));
		 selCand.setStatus(jobAppDetails.getStatus());
		 
		 
		   
	      HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<SelectedCandidates> entity1 = new HttpEntity<SelectedCandidates>(selCand, headers);
	      
	     
	      String url1 = "http://localhost:8091/JobPathFinder/api/v1/addCandidates";
	      
	      ResponseEntity<String> response = restTemplate.exchange(url1, HttpMethod.POST, entity1, String.class);
	      String responseString = response.getBody();
	      System.out.println("responseString" + responseString);
	      //////////////////////////////////////////////////////////////////////////////
	      /*
	      Email email = new Email();
	     email.setSubject("Demo");
	     email.setBody("hello hi how are u");
	     email.setToMail(jobAppDetails.getJobseeker().getEmail());

		*/
	     String firstName = jobAppDetails.getJobseeker().getFirstName();
		String lastName = jobAppDetails.getJobseeker().getLastName();

		String body = "<html><head>";
		body += "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">";
		body += "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css\">";
		body += "</head><body>";
		body += "<div class=\"container\">";
		body += "<div class=\"card mt-5\">";
		body += "<div class=\"card-body\">";
		body += "<h2 class=\"card-title\">Congratulations, " + firstName + " " + lastName + "!</h2>";
		body += "<p class=\"card-text\">We are thrilled to offer you the position of "+jobAppDetails.getVacancy().getJobTitle()+" at "+jobAppDetails.getVacancy().getCompany().getCompanyName()+".</p>";
		body += "<p class=\"card-text\">As part of our team, you will enjoy a range of benefits, including health insurance, retirement plans, and more.</p>";
		body += "<p class=\"card-text\">Your role will involve "+jobAppDetails.getVacancy().getDescription()+". We have a comprehensive onboarding process in place to ensure a smooth transition into your new role.</p>";
		body += "<p class=\"card-text\">You will be joining a dynamic team of professionals, including Nithin in various roles.</p>";
		body += "<p class=\"card-text\">At "+jobAppDetails.getVacancy().getCompany().getCompanyName()+", we are committed to your professional growth and offer opportunities for career advancement through Career Development Programs.</p>";
		body += "<p class=\"card-text\">We are proud of our recent achievements, including "+jobAppDetails.getVacancy().getCompany().getCompanyLevel()+", and look forward to achieving even more with your contribution.</p>";
		body += "<p class=\"card-text\">Connect with us on social media:</p>";
		body += "<ul class=\"list-unstyled\">";
		body += "<li><a href=\"https://twitter.com/?lang=en-in\"><i class=\"bi bi-twitter\"></i> Twitter</a></li>";
		body += "<li><a href=\"https://www.linkedin.com/login\"><i class=\"bi bi-linkedin\"></i> LinkedIn</a></li>";
		body += "<li><a href=\"https://www.facebook.com/\"><i class=\"bi bi-facebook\"></i> Facebook</a></li>";
		body += "</ul>";
		body += "<p class=\"card-text\">Final Score: " + jobAppDetails.getFinalScore() + "</p>";

		body += "<p class=\"card-text\">If you have any questions, please refer to our FAQs <a href=\"" + jobAppDetails.getVacancy().getCompany().getUrl() + "\">here</a> or feel free to reach out to our HR department at "+jobAppDetails.getVacancy().getCompany().getUrl()+" or "+jobAppDetails.getVacancy().getCompany().getMobile()+".</p>";
		body += "<p class=\"card-text\">Next steps include signing the offer letter, completing background checks, and scheduling a start date. We will guide you through this process.</p>";
		body += "    <img th:src=\"@{'http://localhost:8091/JobPathFinder/uploads/{logo}(logo=' + ${jobapp.getVacancy().getCompany().getLogo()} + ')}\" alt=\"Company Logo\" class=\"img-fluid\">\r\n"
				+ "";

		body += "<img src=\"http://localhost:8091/JobPathFinder/uploads/" + jobAppDetails.getVacancy().getCompany().getLogo() + "\" alt=\"Company Logo\" class=\"img-fluid\">";
		body += "<p class=\"card-text mt-5\">Regards,<br>CEO/Management<br>"+jobAppDetails.getVacancy().getCompany().getCompanyName()+"</p>";
		body += "</div>";
		body += "</div>";
		
		body += "</div>";
		body += "</body></html>";


		String subject = "Congratulations on Your Job Offer at "+jobAppDetails.getVacancy().getCompany().getCompanyName()+"";

		String email=jobAppDetails.getJobseeker().getEmail();
		Email mail = new Email();
		mail.setToMail(email);
		mail.setSubject(subject);
		mail.setBody(body);
		

	    HttpHeaders headers1 = new HttpHeaders();
	    headers1.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	     
	     HttpEntity<Email> entity2 = new HttpEntity<Email>(mail, headers);
	      
	     
	      String url3 = "http://localhost:8091/JobPathFinder/sendMail";
	      
	      ResponseEntity<String> response3 = restTemplate.exchange(url3, HttpMethod.POST, entity2, String.class);
	      String responseString1 = response.getBody();
	   
		return "redirect:/company/viewCompanyResults";
		  
	  }
	  
	 /* 
	  @GetMapping("/editCompanyDetails")
		public ModelAndView getvacancy(Model model,HttpSession session) {
			Company company = (Company) session.getAttribute("company");
			int cid = company.getCompanyId();
			System.out.println("cid ?? ==" +cid);
			
			RestTemplate restTemplate = new RestTemplate();	
			ResponseEntity<Company> responseEntity = restTemplate.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getCompany/" + cid,
					Company.class);
			Company comp = responseEntity.getBody();
			System.out.println("url od this company:"+comp.getCompanyName());
			model.addAttribute("companie", comp);
			ModelAndView view = new ModelAndView("editCompanyFields");
			return view;
		}
	  
	  */
	  
/*
	  @PostMapping("/updateCompFields")
		public String updatevacancy(@ModelAttribute("companie") Company compani, Model model) {
			
			System.out.println("name of companie" +compani.getCompanyName());
			
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<Company> entity = new HttpEntity<Company>(compani, headers);

			RestTemplate restTemplate = new RestTemplate();

			String url = "http://localhost:8091/JobPathFinder/api/v1/updateCompany/"+compani.getCompanyId();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

			String responseString = response.getBody();
			System.out.println("responseString11" + responseString);

			return "redirect:/company/getProfile";		
		}
	  
	  */
	  
	  @GetMapping("/editCompanyDetails")
		public ModelAndView editCompanyDetails(Model model, HttpSession session) {
			Company company = (Company) session.getAttribute("company");

			int cid = company.getCompanyId();
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Company> responseEntity = restTemplate
					.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getCompany/" + cid, Company.class);
			Company company1 = responseEntity.getBody();

			System.out.println("company = " + company1);
			model.addAttribute("company", company1);
			
			Company comp=new Company();
			model.addAttribute("comp", comp);
			ModelAndView view = new ModelAndView("editCompanyFields");

			return view;

		}
	  
	  @PostMapping("/updatePic")
		public String addProducts(@ModelAttribute("company") Company company,
				@RequestParam("logo1") MultipartFile logo1, Model model,HttpSession session) {
			// Set the product picture name and save it to a temporary location
			///////////////////////////////////////////////////////////////
		  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String encryptedPassword = passwordEncoder.encode(company.getPassword());

	        // Get the existing user by email
	        Users existingUser = userService.getUsersByEmail(company.getEmail());

	        if (existingUser != null) {
	            // Update the existing user entity with new information
	            existingUser.setPassword(encryptedPassword);
	            existingUser.setRole("ROLE_COMPANY"); // Assuming role is also updated

	            // Save the updated user entity
	            userService.updateUsers(existingUser);
	            System.out.println("User updated successfully");
	        } else {
	            System.out.println("User not found");
	        }
			
			try {

				// Create the upload directory if not exists
				Files.createDirectories(Path.of("./temp_uploads"));

				byte[] picBytes = logo1.getBytes();
				String companyPicName = "temp_" + System.currentTimeMillis() + "_" + logo1.getOriginalFilename();
				Files.write(Paths.get("./temp_uploads", companyPicName), picBytes);

				// Set the product picture name in the product object
				company.setLogo(companyPicName);

				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

				HttpEntity<Company> entity = new HttpEntity<Company>(company, headers);

				RestTemplate restTemplate = new RestTemplate();

				String url = "http://localhost:8091/JobPathFinder/api/v1/updateCompany/"+company.getCompanyId();
				ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

				// System.out.println("responseFlag=" + responseFlag);

				// Up to here product is adding

				//////////////////////// code to upload product picture
				HttpHeaders picHeaders = new HttpHeaders();
				picHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
				MultiValueMap<String, Object> picMap = new LinkedMultiValueMap<>();
				picMap.add("file", new FileSystemResource("./temp_uploads/" + companyPicName));
				picMap.add("filename", companyPicName);
				HttpEntity<MultiValueMap<String, Object>> picEntity = new HttpEntity<>(picMap, picHeaders);

				// Send a request to upload the product picture
				String uploadPicUrl = "http://localhost:8091/JobPathFinder/api/files/upload";
				ResponseEntity<String> picResponse = restTemplate.exchange(uploadPicUrl, HttpMethod.POST, picEntity,
						String.class);

				String picResponseString = picResponse.getBody();

				// Clean up: Delete the temporary product picture
				Files.deleteIfExists(Paths.get("./temp_uploads", companyPicName));

				///////////////////////

				company = new Company();
				model.addAttribute("comp", company);
				

				return "redirect:/company/getProfile";

			} catch (IOException e) {
				// Handle exceptions related to file operations or HTTP requests

				
				return null;
			}

		}
	  
	  
	  
	  

}
