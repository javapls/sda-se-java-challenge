package com.sda.challenge.facade.challengeb.adapter.kafka.consumer;

import com.sda.challenge.facade.challengeb.adapter.kafka.producer.CheckProducer;
import com.sda.challenge.facade.challengeb.domain.model.kafka.CheckEvent;
import com.sda.challenge.facade.challengeb.domain.model.kafka.CheckResultEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class CheckResultConsumer {

    private ConcurrentHashMap<String, CompletableFuture<CheckResultEvent>> futureMap = new ConcurrentHashMap<>();

    @KafkaListener(topics = "${spring.kafka.topic.check-result}", groupId = "${spring.kafka.consumer.group-id}")
    public void checkEventConsumer(ConsumerRecord<String, CheckResultEvent> record) {
        log.info("Receiving CheckResultEvent.");
        log.debug("CheckResultEvent: " + record);
        String correlationId = new String(record.headers().lastHeader("correlationId").value());
        CheckResultEvent event = record.value();
        CompletableFuture<CheckResultEvent> future = futureMap.remove(correlationId);
        if (future != null) {
            future.complete(event);
        }
    }

    public CompletableFuture<CheckResultEvent> sendMessageAndReceive(CheckEvent msg, CheckProducer producer) {
        log.info("Creating CheckEvent.");
        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<CheckResultEvent> future = new CompletableFuture<>();
        futureMap.put(correlationId, future);

        producer.sendMessage(msg, correlationId);

        log.info("Message sent.");
        return future;
    }

}
