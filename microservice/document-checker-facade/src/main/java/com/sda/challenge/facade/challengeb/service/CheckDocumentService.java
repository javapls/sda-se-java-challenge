package com.sda.challenge.facade.challengeb.service;


import com.sda.challenge.facade.challengeb.domain.model.kafka.CheckDocumentRequest;
import com.sda.challenge.facade.challengeb.domain.model.kafka.CheckResultEvent;

import java.util.concurrent.CompletableFuture;

public interface CheckDocumentService {
    CompletableFuture<CheckResultEvent> produce(CheckDocumentRequest checkDocumentRequest);
}
