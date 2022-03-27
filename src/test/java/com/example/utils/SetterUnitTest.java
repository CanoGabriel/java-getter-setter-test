package com.example.utils;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.function.Executable;

public class SetterUnitTest implements Executable, CustomTypeRegisterer {
  private Field field;
  private Method setter;
  private Object instance;
  Class<?> fieldType;

  private boolean isSupportedType(String typeName){
      return registeredCustomType.containsKey(typeName);
  }

  private Map<String, ICustomTypeHandler> registeredCustomType = new HashMap<>();

  public void registerSupportedType(Class<?> clazz, ICustomTypeHandler config) {
      registeredCustomType.put(clazz.getName(), config);
  }

  public Object getExpectedValue() {
      if(!fieldType.isPrimitive() && !isSupportedType(fieldType.getName())){
          return null;
      }

      // Primitive suppport
      Class<?> fieldType = field.getType();
      if (fieldType == byte.class) {
          return Byte.valueOf((byte) 1);
      } else if (fieldType == short.class) {
          return Short.valueOf((short) 1);
      } else if (fieldType == int.class) {
          return Integer.valueOf(1);
      } else if (fieldType == long.class) {
          return Long.valueOf(1L);
      } else if (fieldType == float.class) {
          return Float.valueOf((float) 1);
      } else if (fieldType == double.class) {
          return Double.valueOf(1);
      } else if (fieldType == char.class) {
          return Character.valueOf('a');
      } else if (fieldType == boolean.class) {
          return Boolean.valueOf(true);
      }

      ICustomTypeHandler config = registeredCustomType.get(fieldType.getName());
      return config.getDefaultInstance();
  }

  public SetterUnitTest(Field field, Method setter, Object instance) {
      this.field = field;
      this.setter = setter;
      this.instance = instance;
      this.fieldType = field.getType();
  }

  @Override
  public void execute() throws Throwable {
      if (!fieldType.isPrimitive() && !isSupportedType(fieldType.getName())) {
          throw new Exception("Unsupported type : " + fieldType);
      }
      
      Object expectedValue = getExpectedValue();
      
      if (fieldType.isPrimitive()) {
          switch (fieldType.getName()) {
              case "int":
                  setter.invoke(instance, (int) expectedValue);
                  break;
              case "byte":
                  setter.invoke(instance, (byte) expectedValue);
                  break;
              case "short":
                  setter.invoke(instance, (short) expectedValue);
                  break;
              case "long":
                  setter.invoke(instance, (long) expectedValue);
                  break;
              case "char":
                  setter.invoke(instance, (char) expectedValue);
                  break;
              case "boolean":
                  setter.invoke(instance, (boolean) expectedValue);
                  break;
              case "float":
                  setter.invoke(instance, (float) expectedValue);
                  break;
              case "double":
                  setter.invoke(instance, (double) expectedValue);
                  break;
          }
      } else {
          setter.invoke(instance, fieldType.cast(expectedValue));
      }

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
