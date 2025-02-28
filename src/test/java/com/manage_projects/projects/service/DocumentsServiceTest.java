package com.manage_projects.projects.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.manage_projects.projects.entity.Documents;
import com.manage_projects.projects.repository.DocumentsRepository;

@ExtendWith(MockitoExtension.class)
class DocumentsServiceTest {

    @Mock
    private DocumentsRepository docsRepository;

    @InjectMocks
    private DocumentsService documentsService;

    private Documents testDocument;
    private String projectId;
    private String documentId;

    @BeforeEach
    void setUp() {
        projectId = UUID.randomUUID().toString();
        documentId = UUID.randomUUID().toString();

        testDocument = new Documents();
        testDocument.setId(documentId);
        testDocument.setprojectid(projectId);
        testDocument.setFilename("test.pdf");
        testDocument.setFiletype("application/pdf");
        testDocument.setFilesize("1024");
        testDocument.setFiledata(new byte[]{1, 2, 3});
        testDocument.setCreatedat(LocalDateTime.now());
    }

    @Test
    void testUploadDocuments() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.pdf",
                "application/pdf",
                new byte[]{1, 2, 3}
        );

        when(docsRepository.save(any(Documents.class))).thenReturn(testDocument);

        Documents savedDoc = documentsService.uploadDocuments(file, projectId);

        assertNotNull(savedDoc);
        assertEquals("test.pdf", savedDoc.getFilename());
        assertEquals("application/pdf", savedDoc.getFiletype());
        assertEquals("3", savedDoc.getFilesize()); // file size as string
        assertNotNull(savedDoc.getCreatedat());

        verify(docsRepository, times(1)).save(any(Documents.class));
    }

    @Test
    void testGetDocuments() {
        when(docsRepository.findById(documentId)).thenReturn(Optional.of(testDocument));

        Optional<Documents> foundDoc = documentsService.getDocuments(documentId);

        assertTrue(foundDoc.isPresent());
        assertEquals(documentId, foundDoc.get().getId());

        verify(docsRepository, times(1)).findById(documentId);
    }

    @Test
    void testGetUserDocuments() {
        when(docsRepository.findByProjectId(projectId)).thenReturn(List.of(testDocument));

        List<Documents> docs = documentsService.getUserDocuments(projectId);

        assertFalse(docs.isEmpty());
        assertEquals(1, docs.size());
        assertEquals(projectId, docs.get(0).getprojectid());

        verify(docsRepository, times(1)).findByProjectId(projectId);
    }

    @Test
    void testDeleteDocument_Success() {
        when(docsRepository.findById(documentId)).thenReturn(Optional.of(testDocument));

        documentsService.deleteDocument(documentId);

        verify(docsRepository, times(1)).deleteById(documentId);
    }

    @Test
    void testDeleteDocument_NotFound() {
        when(docsRepository.findById(documentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> documentsService.deleteDocument(documentId));

        assertEquals("Document not found with id " + documentId, exception.getMessage());
        verify(docsRepository, times(1)).findById(documentId);
        verify(docsRepository, never()).deleteById(anyString());
    }

    @Test
    void testDeleteProjectDocuments_Success() {
        when(docsRepository.findByProjectId(projectId)).thenReturn(List.of(testDocument));

        documentsService.deleteProjectDocuments(projectId);

        verify(docsRepository, times(1)).findByProjectId(projectId);
        verify(docsRepository, times(1)).deleteById(documentId);
    }

    @Test
    void testDeleteProjectDocuments_NoDocumentsFound() {
        when(docsRepository.findByProjectId(projectId)).thenReturn(List.of());

        Exception exception = assertThrows(RuntimeException.class, () -> documentsService.deleteProjectDocuments(projectId));

        assertEquals("No documents found for project with id " + projectId, exception.getMessage());
        verify(docsRepository, times(1)).findByProjectId(projectId);
        verify(docsRepository, never()).deleteById(anyString());
    }
}
