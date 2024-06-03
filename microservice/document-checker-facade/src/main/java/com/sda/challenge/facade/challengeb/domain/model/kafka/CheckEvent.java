package com.sda.challenge.facade.challengeb.domain.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckEvent {

  public static final String HEADER_REQUESTER_SERVICE = "requester_service";

  private String url;

  private String fileType;

}
