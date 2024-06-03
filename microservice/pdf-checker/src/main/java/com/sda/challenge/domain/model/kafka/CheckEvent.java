package com.sda.challenge.domain.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckEvent implements Serializable {

  public static final String HEADER_REQUESTER_SERVICE = "requester_service";

  private String url;

  private String fileType;

}
