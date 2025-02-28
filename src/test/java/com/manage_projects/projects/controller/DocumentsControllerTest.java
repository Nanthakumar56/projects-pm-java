package com.manage_projects.projects.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.manage_projects.projects.dto.DocumentResponse;
import com.manage_projects.projects.entity.Documents;
import com.manage_projects.projects.service.DocumentsService;

@ExtendWith(MockitoExtension.class)
class DocumentsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DocumentsService docsService;

    @InjectMocks
    private DocumentsController documentsController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(documentsController).build();
    }

    @Test
    void testUploadFiles() throws Exception {
        MockMultipartFile file = new MockMultipartFile("files", "test.txt", "text/plain", "sample content".getBytes());
        Documents document = new Documents();
        document.setId("123");

        when(docsService.uploadDocuments(any(), anyString())).thenReturn(document);

        mockMvc.perform(multipart("/documents/upload/1")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.docIds[0]").value("123"));
    }

    @Test
    void testGetDocuments() throws Exception {
        Documents document = new Documents();
        document.setId("123");
        document.setFilename("test.txt");
        document.setFiletype("text/plain");
        document.setFiledata("sample content".getBytes());

        when(docsService.getDocuments("123")).thenReturn(Optional.of(document));

        mockMvc.perform(get("/documents/123"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "text/plain"))
                .andExpect(header().string("Content-Disposition", "inline; filename=\"test.txt\""));
    }

    @Test
    void testGetDocuments_NotFound() throws Exception {
        when(docsService.getDocuments("123")).thenReturn(Optional.empty());

        mockMvc.perform(get("/documents/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUserDocuments() throws Exception {
        DocumentResponse doc = new DocumentResponse(
            "123",
            "test.txt",
            "text/plain",
            "1024", 
            "http://localhost:5858/documents/123"
        );

        List<DocumentResponse> documents = Arrays.asList(doc);

        when(docsService.getUserDocuments("1")).thenReturn(Arrays.asList(
            new Documents("123","1", "test.txt", "text/plain", "1024", null, null)  // Mock entity
        ));

        mockMvc.perform(get("/documents/project")
                .param("projectid", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("123"))
                .andExpect(jsonPath("$[0].name").value("test.txt"))  
                .andExpect(jsonPath("$[0].type").value("text/plain"))  
                .andExpect(jsonPath("$[0].size").value("1024"))
                .andExpect(jsonPath("$[0].url").value("http://localhost:5858/documents/123"));
    }

    @Test
    void testGetUserDocuments_NotFound() throws Exception {
        when(docsService.getUserDocuments("1")).thenReturn(List.of());

        mockMvc.perform(get("/documents/project")
                .param("projectid", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No documents found for the user."));
    }

    @Test
    void testDeleteDocument() throws Exception {
        doNothing().when(docsService).deleteDocument("123");

        mockMvc.perform(delete("/documents/delete")
                .param("id", "123"))
                .andExpect(status().isOk())
                .andExpect(content().string("File deleted successfully"));
    }

    @Test
    void testDeleteAllDocuments() throws Exception {
        doNothing().when(docsService).deleteProjectDocuments("1");

        mockMvc.perform(delete("/documents/deletebyProject")
                .param("projectid", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("File deleted successfully"));
    }
}
