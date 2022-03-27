package com.example.utils.runner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.example.utils.BeanUnitTestConfig;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;

public class AutoGetterUnitTest extends AutoUnitTest {
  Field field;
  BeanUnitTestConfig configuration;

  private String generateGetterName(String fieldName) {
    return String.format("get%s%s", fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));
  }

  @Override
  public String getTestName() {
    String fieldName = field.getName();
    String getterName = generateGetterName(fieldName);
    return String.format("Getter %s must get %s field", getterName, fieldName);
  }

  @Override
  public Method getMethodToTest(Object instance) {
    Method accessor = null;
    String methodName = generateGetterName(field.getName());
    try {
      accessor = instance.getClass().getDeclaredMethod(methodName);
    } catch (NoSuchMethodException noSuchMethodException) {
      // TODO logger warning, mandatory check ?
      System.out.println(String.format("No method %s declared", methodName));
    }

    return accessor;
  }

  @Override
  public List<DynamicTest> getMethodDynamicTestSuite(Object instance, Method getter) {
    List<DynamicTest> result = new ArrayList<>();

    // insert auto test from Executable template
    if(getter != null){
      result.add(DynamicTest.dynamicTest(getTestName(), new GetterTestExecutable(instance, field, getter)));
    }

    return result;
  }

  public AutoGetterUnitTest(Field field, BeanUnitTestConfig configuration) {
    this.field = field;
    this.configuration = configuration;
  }

  private class GetterTestExecutable implements Executable {
    private Object instance;
    private Field field;
    private Method getter;

    public GetterTestExecutable(Object instance, Field field, Method getter) {
      this.instance = instance;
      this.field = field;
      this.getter = getter;
    }

    @Override
    public void execute() throws Throwable {
      Class<?> fieldType = field.getType();
      if (!configuration.isSupportedType(fieldType)) {
        throw new Exception("Unsupported type : " + fieldType);
      }

      Object expectedValue = getter.invoke(instance);
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
