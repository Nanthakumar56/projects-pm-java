package com.manage_projects.projects.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.manage_projects.projects.dto.DocumentResponse;
import com.manage_projects.projects.entity.Documents;
import com.manage_projects.projects.service.DocumentsService;

@RestController
@RequestMapping("/documents")
public class DocumentsController {

	@Autowired
	private DocumentsService docsService;
	
	@PostMapping("/upload/{projectid}")
	public ResponseEntity<Map<String, Object>> uploadFiles(@RequestParam("files") MultipartFile[] files,
	                                                       @PathVariable String projectid) {
	    List<String> documentIds = new ArrayList<>();
	    
	    for (MultipartFile file : files) {
	        Documents document = docsService.uploadDocuments(file, projectid);
	        documentIds.add(document.getId());
	    }
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("docIds", documentIds);
	    
	    return ResponseEntity.ok(response);
	}

	 @GetMapping("/{id}")
	    public ResponseEntity<byte[]> getDocuments(
	            @PathVariable String id,
	            @RequestParam(required = false, defaultValue = "false") boolean download) {
	        Optional<Documents> documents = docsService.getDocuments(id);
	        if (documents.isPresent()) {
	        	Documents img = documents.get();
	            HttpHeaders headers = new HttpHeaders();
	            headers.add(HttpHeaders.CONTENT_TYPE, img.getFiletype());
	            
	            if (download) {
	                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + img.getFilename() + "\"");
	            } else {
	                headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + img.getFilename() + "\"");
	            }

	            return new ResponseEntity<>(img.getFiledata(), headers, HttpStatus.OK);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    @GetMapping("/project")
	    public ResponseEntity<?> getUserDocuments(@RequestParam String projectid) {
	        List<Documents> documents = docsService.getUserDocuments(projectid);
	        
	        if (documents != null && !documents.isEmpty()) {
	            List<DocumentResponse> responseList = new ArrayList<>();
	            
	            for (Documents doc : documents) {
	                String fileUrl = "http://localhost:5858/documents/" + doc.getId();  
	                DocumentResponse response = new DocumentResponse(
	                    doc.getId(),
	                    doc.getFilename(),
	                    doc.getFiletype(),
	                    doc.getFilesize(),
	                    fileUrl
	                );
	                responseList.add(response);
	            }
	            
	            return new ResponseEntity<>(responseList, HttpStatus.OK);
	        } else {
	        	Map<String, String> noDocsResponse = new HashMap<>();
	            noDocsResponse.put("message", "No documents found for the user.");
	            
	            return new ResponseEntity<>(noDocsResponse, HttpStatus.NOT_FOUND);
	        }
	    }
	    
	    @DeleteMapping("/delete")
	    public ResponseEntity<String> deleteDocument(@RequestParam String id) {
	        docsService.deleteDocument(id);
	        return ResponseEntity.ok("File deleted successfully");
	    }
	    
	    @DeleteMapping("/deletebyProject")
	    public ResponseEntity<String> deleteAllDocument(@RequestParam String projectid) {
	        docsService.deleteProjectDocuments(projectid);
	        return ResponseEntity.ok("File deleted successfully");
	    }

}
