package com.manage_projects.projects.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.manage_projects.projects.entity.Documents;
import com.manage_projects.projects.repository.DocumentsRepository;

@Service
public class DocumentsService {

    private final Path fileStorageLocation = Paths.get("documents").toAbsolutePath().normalize();
    
    @Autowired
	private DocumentsRepository docsRepository;
    
    public DocumentsService() {
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory to store files.", ex);
        }
    }
    
    public Documents uploadDocuments(MultipartFile file, String projectid) {
        String fileId = UUID.randomUUID().toString();
        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();
        long fileSize = file.getSize();

        try {
            Documents documents = new Documents();
            documents.setprojectid(projectid);
            documents.setFilename(fileName);
            documents.setFiletype(fileType);
            documents.setFilesize(String.valueOf(fileSize));
            documents.setFiledata(file.getBytes()); 
            documents.setCreatedat(LocalDateTime.now());

            docsRepository.save(documents);

            return documents;

        } catch (IOException ex) {
            throw new RuntimeException("Could not store the file " + fileName + ". Please try again!", ex);
        }
    }
    
    public Optional<Documents> getDocuments(String id) {
        return docsRepository.findById(id);
    }
    
    public List<Documents> getUserDocuments(String projectid) {
        return docsRepository.findByProjectId(projectid);
    }
    public void deleteDocument(String docId) {
        Optional<Documents> docs = docsRepository.findById(docId);
        if (docs.isPresent()) {
            docsRepository.deleteById(docId);
        } else {
            throw new RuntimeException("Document not found with id " + docId);
        }
    }
    public void deleteProjectDocuments(String projectid) {
        List<Documents> docs = docsRepository.findByProjectId(projectid);
        if (docs.isEmpty()) {
            throw new RuntimeException("No documents found for project with id " + projectid);
        }

        for (Documents doc : docs) {
            docsRepository.deleteById(doc.getId()); 
        }
    }

}
