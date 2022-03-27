package com.example.utils;

import java.util.HashMap;
import java.util.Map;

public class BeanUnitTestConfig {
  private Map<Class<?>, ICustomTypeHandler> customTypeMap = new HashMap<>();
  
  public boolean isSupportedType(Class<?> typeClass){
    return typeClass.isPrimitive() || customTypeMap.containsKey(typeClass);
  }

  public ICustomTypeHandler getCustomTypeHandler(Class<?> typeClass) throws Exception {
    if(isSupportedType(typeClass)){
      return customTypeMap.get(typeClass);
    }
    // TODO create custom exception
    throw new Exception(String.format("The type '%s' is not supported", typeClass));
  }

  public void addCustomTypeHandler(Class<?> customTypeClass, ICustomTypeHandler handler){
    customTypeMap.put(customTypeClass, handler);
  }

  public static BeanUnitTestConfig merge(BeanUnitTestConfig base, BeanUnitTestConfig source){
    BeanUnitTestConfig result = new BeanUnitTestConfig();
    result.customTypeMap.putAll(base.customTypeMap);
    result.customTypeMap.putAll(source.customTypeMap);

    return result;
  }

  public BeanUnitTestConfig(){
    // register primitive handler
    addCustomTypeHandler(boolean.class, ICustomTypeHandler.PRIMITIVE_HANDLER_BOOLEAN);
    addCustomTypeHandler(byte.class, ICustomTypeHandler.PRIMITIVE_HANDLER_BYTE);
    addCustomTypeHandler(char.class, ICustomTypeHandler.PRIMITIVE_HANDLER_CHAR);
    addCustomTypeHandler(double.class, ICustomTypeHandler.PRIMITIVE_HANDLER_DOUBLE);
    addCustomTypeHandler(float.class, ICustomTypeHandler.PRIMITIVE_HANDLER_FLOAT);
    addCustomTypeHandler(int.class, ICustomTypeHandler.PRIMITIVE_HANDLER_INT);
    addCustomTypeHandler(long.class, ICustomTypeHandler.PRIMITIVE_HANDLER_LONG);
    addCustomTypeHandler(short.class, ICustomTypeHandler.PRIMITIVE_HANDLER_SHORT);

    // register primitive wrapper handler
    addCustomTypeHandler(Boolean.class, ICustomTypeHandler.PRIMITIVE_HANDLER_BOOLEAN);
    addCustomTypeHandler(Byte.class, ICustomTypeHandler.PRIMITIVE_HANDLER_BYTE);
    addCustomTypeHandler(Character.class, ICustomTypeHandler.PRIMITIVE_HANDLER_CHAR);
    addCustomTypeHandler(Double.class, ICustomTypeHandler.PRIMITIVE_HANDLER_DOUBLE);
    addCustomTypeHandler(Float.class, ICustomTypeHandler.PRIMITIVE_HANDLER_FLOAT);
    addCustomTypeHandler(Integer.class, ICustomTypeHandler.PRIMITIVE_HANDLER_INT);
    addCustomTypeHandler(Long.class, ICustomTypeHandler.PRIMITIVE_HANDLER_LONG);
    addCustomTypeHandler(Short.class, ICustomTypeHandler.PRIMITIVE_HANDLER_SHORT);

    // TODO retrieve annotated handler
  }
}

