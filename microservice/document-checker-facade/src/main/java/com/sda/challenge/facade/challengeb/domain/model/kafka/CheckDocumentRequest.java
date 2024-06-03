package com.sda.challenge.facade.challengeb.domain.model.kafka;

import lombok.Data;

@Data
public class CheckDocumentRequest {
    String documentUrl;
    String documentType;
}
