package com.sda.challenge.processor;

import com.sda.challenge.domain.model.kafka.CheckEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface CheckDocumentProcessor {

    void processCheckEvent(ConsumerRecord<String, CheckEvent> checkEvent);
}
