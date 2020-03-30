package com.example.reactiveWebTest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Student implements Serializable {
  private String name;
  private int age;
  private Date date;
  private BigDecimal b;
  private Duration d;
}
