package com.example.utils.runner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.example.utils.BeanUnitTestConfig;
import com.example.utils.exception.UnsupportedCustomTypeException;
import com.example.utils.handler.ICustomTypeHandler;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;

public class AutoSetterUnitTest extends AutoUnitTest {
  Field field;
  BeanUnitTestConfig configuration;

  private String generateSetterName(String fieldName) {
    return String.format("set%s%s", fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));
  }

  @Override
  public String getTestName() {
    String fieldName = field.getName();
    String getterName = generateSetterName(fieldName);
    return String.format("Setter %s must set %s field", getterName, fieldName);
  }

  @Override
  public Method getMethodToTest(Object instance) {
    Method accessor = null;
    String methodName = generateSetterName(field.getName());
    try {
      accessor = instance.getClass().getDeclaredMethod(methodName, field.getType());
    } catch (NoSuchMethodException noSuchMethodException) {
      // TODO logger warning, mandatory check ?
      System.out.println(String.format("No method %s declared", methodName));
    }

    return accessor;
  }

  @Override
  public List<DynamicTest> getMethodDynamicTestSuite(Object instance, Method setter) {
    List<DynamicTest> result = new ArrayList<>();
    
    // insert auto test from Executable template
    if(setter != null){
      result.add(DynamicTest.dynamicTest(getTestName(), new SetterTestExecutable(instance, field, setter)));
    }
    return result;
  }

  public AutoSetterUnitTest(Field field, BeanUnitTestConfig configuration) {
    this.field = field;
    this.configuration = configuration;
  }

  private class SetterTestExecutable implements Executable {
    private Object instance;
    private Field field;
    private Class<?> fieldType;
    private Method setter;

    public SetterTestExecutable(Object instance, Field field, Method setter) {
      this.instance = instance;
      this.field = field;
      this.fieldType = field.getType();
      this.setter = setter;
    }

    public Object getExpectedValue() throws UnsupportedCustomTypeException {
      ICustomTypeHandler config = configuration.getCustomTypeHandler(fieldType);
      return config.getDefaultInstance();
    }

    @Override
    public void execute() throws Throwable {
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
      field.setAccessible(true);
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
}
