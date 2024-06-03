package com.sda.challenge.processor;

import com.sda.challenge.adapter.kafka.producer.CheckResultProducer;
import com.sda.challenge.domain.enums.StateEnum;
import com.sda.challenge.domain.model.kafka.CheckEvent;
import com.sda.challenge.domain.model.kafka.CheckResultEvent;
import com.sda.challenge.service.DocumentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;


@Slf4j
@AllArgsConstructor
@Service
public class CheckDocumentProcessorImpl implements CheckDocumentProcessor {

    private final CheckResultProducer checkResultProducer;

    private DocumentService documentService;

    @Override
    public void processCheckEvent(ConsumerRecord<String, CheckEvent> checkEvent) {
        log.info("Processing Document.");
        CheckResultEvent checkResult = new CheckResultEvent();
        String correlationId = null;

        String downloadFilePath;
        File file = null;
        try {
            correlationId = new String(checkEvent.headers().lastHeader("correlationId").value());
            log.info("Processing correlationId: " + correlationId);

            downloadFilePath = "src/main/resources/pdf/"+correlationId+".pdf";
            file = documentService.getDocument(checkEvent.value().getUrl(), downloadFilePath);
            checkResult.setName(file.getName());
            checkResult.setState(validateDocument(file));
        } catch (IOException e) {
            log.error("CheckDocumentProcessor - processCheckEvent - Some Error Occurred.");
            log.error(e.getMessage());
            checkResult.setState(StateEnum.ERROR);
        }

        checkResult.setDetails(generateDetails(checkResult.getState(), checkResult.getName()));

        if(file != null) {
            boolean deletedFile = file.delete();
            log.info("File was Delete: " + deletedFile);
        }

        log.info("Document Validation Finished.");
        checkResultProducer.sendMessage(checkResult,correlationId);
    }

    private StateEnum validateDocument(File file) {
        try {

            if(documentService.isInvalidDocument(file)) {
                log.info("The File is Invalid.");
                return StateEnum.SUSPICIOUS;
            }

            return documentService.allIbansIsValid(file);
        } catch (Exception e) {
            log.error("CheckDocumentProcessor - validateDocument - Some Error Occurred.");
            log.error(e.getMessage());
            return StateEnum.ERROR;
        }
    }

    private String generateDetails(StateEnum stateEnum, String fileName) {
        switch (stateEnum) {
            case OK:
                return String.format("The File %s is Valid.", fileName);
            case SUSPICIOUS:
                return  String.format("The File %s has a scam.", fileName);
            case IGNORED:
                return String.format("This File %s is valid, but does not contain any IBAN.", fileName);
            case ERROR:
                return "Some Error Occurs";
            default:
                return "Invalid State";
        }
    }

}
