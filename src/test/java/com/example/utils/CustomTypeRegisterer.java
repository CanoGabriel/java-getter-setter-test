package com.example.utils;

public interface CustomTypeRegisterer {
  void registerSupportedType(Class<?> clazz, ICustomTypeHandler config);

}
