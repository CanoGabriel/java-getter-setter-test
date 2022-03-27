package com.example.utils.exception;

public class UnsupportedCustomTypeException extends Exception {
  public UnsupportedCustomTypeException(Class<?> type){
    super(String.format("Unsupported type '%s'", type.getName()));
  }
}
