package com.sda.challenge.facade.challengeb.adapter.kafka.producer;

import com.sda.challenge.facade.challengeb.domain.model.kafka.CheckEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CheckProducer {

    @Autowired
    private KafkaTemplate<String, CheckEvent> kafkaTemplate;

    @Value("${spring.kafka.topic.check-validation}")
    private String TOPIC;

    public void sendMessage(CheckEvent msg, String correlationId){
        ProducerRecord<String, CheckEvent> record = new ProducerRecord<>(TOPIC, msg);
        record.headers().add("correlationId", correlationId.getBytes());

        log.debug("CheckEvent: " + msg);
        log.debug("correlationId: " + correlationId);
        kafkaTemplate.send(record);

        log.info("Message Send: " + msg);
    }
}
