package com.sda.challenge.domain.model.kafka;

import com.sda.challenge.domain.enums.StateEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CheckResultEvent implements Serializable {

  private StateEnum state;

  private String name;

  private String details;
}
