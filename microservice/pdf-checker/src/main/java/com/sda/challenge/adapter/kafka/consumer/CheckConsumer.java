package com.sda.challenge.adapter.kafka.consumer;

import com.sda.challenge.domain.model.kafka.CheckEvent;
import com.sda.challenge.processor.CheckDocumentProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CheckConsumer {
    @Autowired
    private CheckDocumentProcessor checkService;

    @KafkaListener(topics = "${spring.kafka.topic.check-validation}", groupId = "${spring.kafka.consumer.group-id}")
    public void checkEventConsumer(ConsumerRecord<String, CheckEvent> event) {
        log.info("CheckEventConsumer - Event Received");
        log.debug("Event: " + event);
        checkService.processCheckEvent(event);
    }
}
