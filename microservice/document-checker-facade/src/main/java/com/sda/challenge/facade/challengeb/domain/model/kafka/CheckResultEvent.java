package com.sda.challenge.facade.challengeb.domain.model.kafka;

import com.sda.challenge.facade.challengeb.domain.model.enums.StateEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckResultEvent {

  private StateEnum state;

  private String name;

  private String details;
}
