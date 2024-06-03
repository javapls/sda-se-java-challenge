package com.sda.challenge.facade.challengeb.processor;

import com.sda.challenge.facade.challengeb.domain.model.kafka.CheckDocumentRequest;
import com.sda.challenge.facade.challengeb.domain.model.kafka.CheckResultEvent;
import com.sda.challenge.facade.challengeb.service.CheckDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class CheckDocumentProcessorImpl implements CheckDocumentProcessor {

    @Autowired
    private CheckDocumentService checkDocumentService;

    @Override
    public CheckResultEvent checkDocument(CheckDocumentRequest checkDocumentRequest) throws ExecutionException, InterruptedException {
        log.info("CheckDocumentProcessor");
        return checkDocumentService.produce(checkDocumentRequest).get();
    }
}
