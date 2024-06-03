package com.sda.challenge.facade.challengeb.service;


import com.sda.challenge.facade.challengeb.adapter.kafka.consumer.CheckResultConsumer;
import com.sda.challenge.facade.challengeb.adapter.kafka.producer.CheckProducer;
import com.sda.challenge.facade.challengeb.domain.model.kafka.CheckDocumentRequest;
import com.sda.challenge.facade.challengeb.domain.model.kafka.CheckEvent;
import com.sda.challenge.facade.challengeb.domain.model.kafka.CheckResultEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@Slf4j
@AllArgsConstructor
@Service
public class CheckDocumentServiceImpl implements CheckDocumentService {

    @Autowired
    private final CheckResultConsumer checkResultConsumer;

    @Autowired
    private final CheckProducer producer;

    @Override
    public CompletableFuture<CheckResultEvent> produce(CheckDocumentRequest checkDocumentRequest) {
        CheckEvent checkEvent = CheckEvent.builder()
                .url(checkDocumentRequest.getDocumentUrl())
                .fileType(checkDocumentRequest.getDocumentType())
                .build();

        log.info("CheckEvent was Created");
        log.debug("Request Object: "+ checkDocumentRequest);
        log.debug("CheckEvent Object: "+ checkDocumentRequest);

        return checkResultConsumer.sendMessageAndReceive(checkEvent,producer);
    }

}
