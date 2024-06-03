package com.sda.challenge.facade.challengeb.processor;

import com.sda.challenge.facade.challengeb.domain.model.kafka.CheckDocumentRequest;
import com.sda.challenge.facade.challengeb.domain.model.kafka.CheckResultEvent;

import java.util.concurrent.ExecutionException;

public interface CheckDocumentProcessor {
    CheckResultEvent checkDocument(CheckDocumentRequest documentUrl) throws ExecutionException, InterruptedException;

}
