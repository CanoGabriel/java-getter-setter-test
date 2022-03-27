package com.example.utils.handler;

import com.example.utils.annotation.IncludeCustomTypeHandler;

@IncludeCustomTypeHandler(type = String.class)
public class StringHandler implements ICustomTypeHandler {

  @Override
  public Object getDefaultInstance() {
    return "string";
  }
  
}
