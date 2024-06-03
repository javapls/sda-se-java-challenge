package com.sda.challenge.facade.challengeb.controller;

import com.sda.challenge.facade.challengeb.adapter.kafka.api.FacadeApi;
import com.sda.challenge.facade.challengeb.domain.model.enums.StateEnum;
import com.sda.challenge.facade.challengeb.domain.model.kafka.CheckDocumentRequest;
import com.sda.challenge.facade.challengeb.processor.CheckDocumentProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/check-document")
public class FacadeController implements FacadeApi {

    @Autowired
    private CheckDocumentProcessor checkDocumentProcessor;

    @PostMapping("")
    public ResponseEntity<StateEnum> checkDocument(@RequestBody CheckDocumentRequest checkDocumentRequest){
        try {
            log.info("Start Document Validation - URL: /check-document");
            return ResponseEntity.ok(checkDocumentProcessor.checkDocument(checkDocumentRequest).getState());
        } catch (Exception e) {
            log.error("Some Error Occured: ");
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
