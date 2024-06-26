package com.example.demo.controller;

import java.io.IOException;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.example.demo.entity.JobApply;
import com.example.demo.entity.JobSeekers;
import com.example.demo.entity.Questions;
import com.example.demo.entity.Test;
import com.example.demo.entity.Users;
import com.example.demo.entity.Vacancy;
import com.example.demo.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("jobseeker")
public class JobSeekersController {
	@Autowired 
	UsersService userService;
	/////////////////////////////////////jobseeker home 

	@GetMapping("/jobseekerHome")
	public ModelAndView companyHome(Model model, HttpSession session, Authentication authentication,
			JobSeekers jobSeekers) {

		String username = authentication.getName();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<JobSeekers> entity = new HttpEntity<JobSeekers>(jobSeekers, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JobSeekers> response = restTemplate
				.getForEntity("http://localhost:8091/JobPathFinder/api/v1/jobSeekersLogin/" + username, JobSeekers.class);

		JobSeekers responseObject = response.getBody();

		System.out.println("responseFlag=" + responseObject);

		if (responseObject != null) { // login success

			System.out.println("JobSeekersId ==" + responseObject.getUserId());
			session.setAttribute("jobSeekers", responseObject);
			JobSeekers jobSeekers1 = (JobSeekers) session.getAttribute("jobSeekers");

			int userId = jobSeekers1.getUserId();
			model.addAttribute("userId", userId);

			//////////////////////////////////////////////////////////////////////////////////////

			ResponseEntity<Vacancy[]> responseEntity1 = restTemplate
					.getForEntity("http://localhost:8091/JobPathFinder/api/v1/allVacancies", Vacancy[].class);
			Vacancy[] responseObj = responseEntity1.getBody();
			List<Vacancy> vacancyList = Arrays.asList(responseObj);
			System.out.println("vacancyList" + vacancyList);
			model.addAttribute("vacancyList", vacancyList);
			//////////////////////////////////////////////////////
			
			
			
			for (Vacancy vacancy : vacancyList) {
				int vacancyId = vacancy.getVacancyId();
				
				ResponseEntity<Boolean> responseEntity2 = restTemplate
						.getForEntity("http://localhost:8091/JobPathFinder/api/v1/check/" + vacancyId + "/" + userId, Boolean.class);
				Boolean  applied = responseEntity2.getBody();
				session.setAttribute("applied_"+ vacancyId,applied);
				

			}
         
          
			ModelAndView view = new ModelAndView("jobseekerHome");

			return view;

		} else {
			model.addAttribute("resp", "*Email/pass doesnot exists");

			ModelAndView view = new ModelAndView("jobseeker/companylogin");

			return view;

		}
	}
	
	
	/*
	 * @GetMapping("/jobseekerslogin") public ModelAndView jobSeekers(Model model) {
	 * JobSeekers jobSeekers = new JobSeekers();
	 * model.addAttribute("jobSeekers",jobSeekers); ModelAndView view = new
	 * ModelAndView("companylogin"); return view; }
	 */

	/*
	 * @PostMapping("/ValidateJobLogin") public ModelAndView
	 * validateJobSeekersLogin(@ModelAttribute("jobSeekers") JobSeekers
	 * jobSeekers,Model model) { HttpHeaders headers = new HttpHeaders();
	 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	 * 
	 * HttpEntity<JobSeekers> entity = new
	 * HttpEntity<JobSeekers>(jobSeekers,headers);
	 * 
	 * RestTemplate restTemplate = new RestTemplate(); ResponseEntity<Boolean>
	 * response =
	 * restTemplate.exchange("http://localhost:8091/JobPathFinder/api/v1/jobseekersLogin",
	 * HttpMethod.POST,entity,Boolean.class);
	 * 
	 * boolean responseFlag = response.getBody();
	 * System.out.println("responseFlag =" +responseFlag);
	 * 
	 * if(responseFlag == true) { ModelAndView view = new
	 * ModelAndView("jobseekerHome"); return view; }else {
	 * model.addAttribute("responseText","Email/Password incorrect...");
	 * ModelAndView view = new ModelAndView("/companylogin"); return view; }
	 * 
	 * }
	 */


	@GetMapping("/getJobSeekerProfile")
	public ModelAndView getJobSeekerProfile(Model model, HttpSession session) {
		
		 JobSeekers jobSeekers1 =(JobSeekers) session.getAttribute("jobSeekers");
			int userId = jobSeekers1.getUserId();
			System.out.println("userId" +userId);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JobSeekers> responseEntity = restTemplate
				.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getJobseekers/" + userId, JobSeekers.class);
		JobSeekers job = responseEntity.getBody();
		String imageUrl = job.getProfilePic();
		job.setProfilePic(imageUrl);
		System.out.println("JobSeekers = " + job.getFirstName());
		model.addAttribute("jobApplicants", job);
		
		ModelAndView view = new ModelAndView("jobseekerProfile");

		return view;

	}
	
	@GetMapping("/editJobProfileDetails")
	public ModelAndView editJobProfileDetails(Model model, HttpSession session) {
		
		JobSeekers job =(JobSeekers) session.getAttribute("jobSeekers");
		int userId=job.getUserId();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JobSeekers> responseEntity = restTemplate
				.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getJobseekers/" + userId, JobSeekers.class);
		JobSeekers job1=responseEntity.getBody();
		System.out.println("job1 = " + job1);
		System.out.println("job1 = " + job1.getFirstName());
		model.addAttribute("jobseeker", job1);
		JobSeekers jobseek = new JobSeekers();
		model.addAttribute("jobseekerr", jobseek);
		ModelAndView view = new ModelAndView("editJobFields");
		return view;

	}
	
	@PostMapping("/updateJob")
	public String addProducts(@ModelAttribute("jobseeker") JobSeekers jobseeker,
			@RequestParam("logo1") MultipartFile logo1, Model model,HttpSession session) {
		// Set the product picture name and save it to a temporary location
		 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String encryptedPassword = passwordEncoder.encode(jobseeker.getPassword());

	        // Get the existing user by email
	        Users existingUser = userService.getUsersByEmail(jobseeker.getEmail());

	        if (existingUser != null) {
	            // Update the existing user entity with new information
	            existingUser.setPassword(encryptedPassword);
	            existingUser.setRole("ROLE_JOBSEEKER"); // Assuming role is also updated

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
			jobseeker.setProfilePic(companyPicName);

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<JobSeekers> entity = new HttpEntity<JobSeekers>(jobseeker, headers);

			RestTemplate restTemplate = new RestTemplate();

			String url = "http://localhost:8091/JobPathFinder/api/v1/updateJobseeker/"+jobseeker.getUserId();
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

			jobseeker = new JobSeekers();
			model.addAttribute("jobseeker", jobseeker);
			

			return "redirect:/jobseeker/getJobSeekerProfile";

		} catch (IOException e) {
			// Handle exceptions related to file operations or HTTP requests

			
			return null;
		}

	}
}
