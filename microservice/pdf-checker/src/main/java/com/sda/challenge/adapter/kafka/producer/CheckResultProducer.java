package com.sda.challenge.adapter.kafka.producer;

import com.sda.challenge.domain.model.kafka.CheckResultEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CheckResultProducer {

    @Autowired
    private KafkaTemplate<String, CheckResultEvent> kafkaTemplate;

    @Value("${spring.kafka.topic.check-result}")
    private String TOPIC;

    public void sendMessage(CheckResultEvent msg, String correlationId) {
        ProducerRecord<String, CheckResultEvent> record = new ProducerRecord<>(TOPIC, msg);
        record.headers().add("correlationId", correlationId.getBytes());
        log.info("CheckResultEvent Has Been Sent.");
        log.debug("CheckResultEvent: " + msg);
        log.debug("correlationId: " + correlationId);
        kafkaTemplate.send(record);
    }
}
