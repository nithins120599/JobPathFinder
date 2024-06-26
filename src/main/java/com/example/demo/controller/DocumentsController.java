package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.example.demo.entity.Documents;
import com.example.demo.entity.JobApply;
import com.example.demo.entity.JobSeekers;
import com.example.demo.entity.Questions;
import com.example.demo.entity.Test;
import com.example.demo.entity.Users;
import com.example.demo.entity.Vacancy;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("jobseeker")
public class DocumentsController {

	@GetMapping("addDocumentss")
	public ModelAndView addQuestions(Model model, HttpSession session) {

		Documents doc = new Documents();

		// Assuming you need JobSeekers information for some purpose
		JobSeekers jobSeekers1 = (JobSeekers) session.getAttribute("jobSeekers");
		int userId = jobSeekers1.getUserId();
		model.addAttribute("userId", userId);
		System.out.println("userId" + userId);
		model.addAttribute("docu", doc);
		ModelAndView view = new ModelAndView("addDocuments");
		return view;
	}

	@PostMapping("/addingDocuments")
	public ModelAndView saveQuestions(@ModelAttribute("doc") Documents doc, Model model, HttpSession session,
			@RequestParam("documentFile1") MultipartFile documentFile1) {

		System.out.println("Documents: " + doc);
		JobSeekers job = new JobSeekers();
		int userId = ((JobSeekers) session.getAttribute("jobSeekers")).getUserId();
		model.addAttribute("userId", userId);
		job.setUserId(userId);
		System.out.println("userId here.." + userId);
		doc.setJobSeekers(job);

/////////////////////////////////////////////////////////////////////////////
/////////////////////
		try {

// Create the upload directory if not exists
			Files.createDirectories(Path.of("./temp_uploads"));

			byte[] picBytes = documentFile1.getBytes();
			String documentFilee = "temp_" + System.currentTimeMillis() + "_" + documentFile1.getOriginalFilename();
			Files.write(Paths.get("./temp_uploads", documentFilee), picBytes);

// Set the product picture name in the product object
			doc.setDocumentFile(documentFilee);

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Documents> entity = new HttpEntity<Documents>(doc, headers);

			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8091/JobPathFinder/api/v1/addDocuments";

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

			String responseString = response.getBody();
			System.out.println("objj =" + responseString);
			///////////////////////////// up to here documents is adding

////////////////////////code to upload product picture
			HttpHeaders picHeaders = new HttpHeaders();
			picHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
			MultiValueMap<String, Object> picMap = new LinkedMultiValueMap<>();
			picMap.add("file", new FileSystemResource("./temp_uploads/" + documentFilee));
			picMap.add("filename", documentFilee);
			HttpEntity<MultiValueMap<String, Object>> picEntity = new HttpEntity<>(picMap, picHeaders);

// Send a request to upload the product picture
			String uploadPicUrl = "http://localhost:8091/JobPathFinder/api/files/upload";
			ResponseEntity<String> picResponse = restTemplate.exchange(uploadPicUrl, HttpMethod.POST, picEntity,
					String.class);

			String picResponseString = picResponse.getBody();

// Clean up: Delete the temporary product picture
			Files.deleteIfExists(Paths.get("./temp_uploads", documentFilee));

///////////////////////

			///////////////////////
			// Clearing the question object after submission
			doc = new Documents();
			model.addAttribute("docu", doc);

			ModelAndView view = new ModelAndView("addDocuments");
			model.addAttribute("responseString", responseString);
		   model.addAttribute("resp","Documents Added Successfully!!");


			return view;
		} catch (IOException e) {
			// Handle exceptions related to file operations or HTTP requests

			ModelAndView errorView = new ModelAndView("/userlogin");
			model.addAttribute("responseString", e.getMessage());
			return errorView;
		}

	}
	
	 @GetMapping("/viewDocumentss")
	  public ModelAndView viewVacancies(Model model,HttpSession session)
		{
		 JobSeekers jobSeekers = (JobSeekers) session.getAttribute("jobSeekers");
		  int userId = jobSeekers.getUserId();

			 RestTemplate restTemplate = new RestTemplate();
			 ResponseEntity<Documents[]> responseEntity = restTemplate  
						.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getDocuments/"+userId, Documents[].class);

			 Documents[] responseBody =  responseEntity.getBody(); 
			 List< Documents> docList = Arrays.asList(responseBody);
			 
			System.out.println("docList "+docList.size());  
			 model.addAttribute("documentList",docList);
			ModelAndView view = new ModelAndView("viewDocuments");
			return view;
		}
	 
	 
	 @GetMapping("/editDocuments/{docId}")
		public ModelAndView getvacancy(@PathVariable("docId") int docId,Model model) {
			
			
			RestTemplate restTemplate = new RestTemplate();	
			ResponseEntity<Documents> responseEntity = restTemplate.getForEntity("http://localhost:8091/JobPathFinder/api/v1/getDoc/" + docId,
					Documents.class);
			Documents doc = responseEntity.getBody();
			System.out.println("Docummmments:" + doc.getDocId());
			System.out.println("Docummmments:" + doc.getDocumentType());
			System.out.println("userId D" +doc.getJobSeekers().getUserId());
			model.addAttribute("docum", doc);
			ModelAndView view = new ModelAndView("editDocument");
			return view;
		}
	 
	 @PostMapping("/updateDocument")
		public String addProducts(@ModelAttribute("docum") Documents document,
				@RequestParam("documentFile1") MultipartFile documentFile1, Model model) {
			// Set the product picture name and save it to a temporary location
			///////////////////////////////////////////////////////////////
		 
			try {

				// Create the upload directory if not exists
				Files.createDirectories(Path.of("./temp_uploads"));

				byte[] picBytes = documentFile1.getBytes();
				String docFile = "temp_" + System.currentTimeMillis() + "_" + documentFile1.getOriginalFilename();
				Files.write(Paths.get("./temp_uploads", docFile), picBytes);

				// Set the product picture name in the product object
				document.setDocumentFile(docFile);

				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

				HttpEntity<Documents> entity = new HttpEntity<Documents>(document, headers);

				RestTemplate restTemplate = new RestTemplate();

				String url = "http://localhost:8091/JobPathFinder/api/v1/updateDocument/"+document.getDocId();
				ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

				// System.out.println("responseFlag=" + responseFlag);

				// Up to here product is adding

				//////////////////////// code to upload product picture
				HttpHeaders picHeaders = new HttpHeaders();
				picHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
				MultiValueMap<String, Object> picMap = new LinkedMultiValueMap<>();
				picMap.add("file", new FileSystemResource("./temp_uploads/" + docFile));
				picMap.add("filename", docFile);
				HttpEntity<MultiValueMap<String, Object>> picEntity = new HttpEntity<>(picMap, picHeaders);

				// Send a request to upload the product picture
				String uploadPicUrl = "http://localhost:8091/JobPathFinder/api/files/upload";
				ResponseEntity<String> picResponse = restTemplate.exchange(uploadPicUrl, HttpMethod.POST, picEntity,
						String.class);

				String picResponseString = picResponse.getBody();

				// Clean up: Delete the temporary product picture
				Files.deleteIfExists(Paths.get("./temp_uploads", docFile));

				///////////////////////

				document = new Documents();
				model.addAttribute("docc", document);
				

				return "redirect:/jobseeker/viewDocumentss";

			} catch (IOException e) {
				// Handle exceptions related to file operations or HTTP requests

				
				return null;
			}

		}
	  
	 @GetMapping("/deleteDocuments/{docId}")
		public String deletevacancy(@PathVariable("docId") int docId) {
		
			Map<String, Integer> pathVar = new HashMap<>();
			pathVar.put("docId", docId);
			
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.delete("http://localhost:8091/JobPathFinder/api/v1/deleteDocument/{docId}",pathVar);
			//ModelAndView view = new ModelAndView("viewProducts");
			//return view;
			return "redirect:/jobseeker/viewDocumentss";
			
		}
	
	 
}
