package com.example.utils.handler;

import java.time.LocalDate;

import com.example.utils.annotation.IncludeCustomTypeHandler;

@IncludeCustomTypeHandler(type = LocalDate.class)
public class LocalDateHandler implements ICustomTypeHandler {

  @Override
  public Object getDefaultInstance() {
    return LocalDate.of(2022, 1, 1);
  }
  
}
