package com.example.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.function.Executable;

public class GetterUnitTest implements Executable, CustomTypeRegisterer {
  private Field field;
  private Method getter;
  private Object instance;
  private Class<?> fieldType;
  private Map<String, ICustomTypeHandler> registeredCustomType = new HashMap<>();

  private boolean isSupportedType(String typeName){
      return registeredCustomType.containsKey(typeName);
  }

  public void registerSupportedType(Class<?> clazz, ICustomTypeHandler config) {
      registeredCustomType.put(clazz.getName(), config);
  }

  public GetterUnitTest(Field field, Method getter, Object instance) {
      this.field = field;
      this.getter = getter;
      this.instance = instance;
      this.fieldType = field.getType();
  }

  @Override
  public void execute() throws Throwable {
      if (!fieldType.isPrimitive() && !isSupportedType(fieldType.getName())) {
          throw new Exception("Unsupported type : " + fieldType);
      }

      Object expectedValue = getter.invoke(instance);
      Object value = field.get(instance);

      if (fieldType.isPrimitive()) {
          switch (fieldType.getName()) {
              case "int":
                  assertEquals((int) expectedValue, (int) value);
                  break;
              case "byte":
                  assertEquals((byte) expectedValue, (byte) value);
                  break;
              case "short":
                  assertEquals((short) expectedValue, (short) value);
                  break;
              case "long":
                  assertEquals((long) expectedValue, (long) value);
                  break;
              case "char":
                  assertEquals((char) expectedValue, (char) value);
                  break;
              case "boolean":
                  assertEquals((boolean) expectedValue, (boolean) value);
                  break;
              case "float":
                  assertEquals((float) expectedValue, (float) value);
                  break;
              case "double":
                  assertEquals((double) expectedValue, (double) value);
                  break;
          }
      } else {
          assertEquals(fieldType.cast(expectedValue), fieldType.cast(value));
      }
  }
}
