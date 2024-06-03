package com.sda.challenge.service;

import com.sda.challenge.adapter.kafka.document.DocumentAdapter;
import com.sda.challenge.domain.enums.StateEnum;
import com.sda.challenge.utils.MockUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceImplTest {

    @InjectMocks
    private DocumentServiceImpl service;

    @Mock
    private DocumentAdapter documentAdapter;

    @Test
    public void shouldValidMagicByteOfAInvalidFile() {
        // Arrange
        File invalidMockFile = MockUtils.getFile("src/test/java/com/sda/challenge/pdf/fake-file.pdf");

        // Act
        boolean isInvalidFile = service.isInvalidDocument(invalidMockFile);

        // Assert
        assertTrue(isInvalidFile);
    }

    @Test
    public void shouldValidMagicByteOfAFileAValidFile() {
        // Arrange
        File invalidMockFile = MockUtils.getFile("src/test/java/com/sda/challenge/pdf/file-ok.pdf");

        // Act
        boolean isInvalidFile = service.isInvalidDocument(invalidMockFile);

        // Assert
        assertFalse(isInvalidFile);
    }

    @Test
    public void shouldReturOkWhenAllIBANSIsValid(){
        // Arrange
        File invalidMockFile = MockUtils.getFile("src/test/java/com/sda/challenge/pdf/file-ok.pdf");

        // Act
        StateEnum stateEnum = service.allIbansIsValid(invalidMockFile);

        // Assert
        assertEquals(StateEnum.OK, stateEnum);

    }

    @Test
    public void shouldReturSuspiciousWhenHasAnBlockedIban(){
        // Arrange
        File invalidMockFile = MockUtils.getFile("src/test/java/com/sda/challenge/pdf/Testdaten_Rechnungseinreichung.pdf");

        // Act
        StateEnum stateEnum = service.allIbansIsValid(invalidMockFile);

        // Assert
        assertEquals(StateEnum.SUSPICIOUS, stateEnum);

    }

    @Test
    public void shouldReturIgnoredWhenHasNotIban(){
        // Arrange
        File invalidMockFile = MockUtils.getFile("src/test/java/com/sda/challenge/pdf/thanks.pdf");

        // Act
        StateEnum stateEnum = service.allIbansIsValid(invalidMockFile);

        // Assert
        assertEquals(StateEnum.IGNORED, stateEnum);
    }

    @Test
    public void shouldCallDocumentAdapter() throws IOException {
        // Arrange
        Mockito.when(documentAdapter.getDocument("MockedUrl", "DownloadTarget")).thenReturn(null);

        // Act
        service.getDocument("MockedUrl", "DownloadTarget");

        // Assert
        Mockito.verify(documentAdapter).getDocument("MockedUrl", "DownloadTarget");
    }
}