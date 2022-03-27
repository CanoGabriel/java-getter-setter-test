package com.example.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.DynamicTest;

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
  public List<Method> getMethodToTest() {
    Method accessor = null;

        try {
            accessor = isSetter ? objClass.getDeclaredMethod(methodName, fieldClass)
                    : objClass.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException noSuchMethodException) {
            System.out.println(String.format("No method %s declared", methodName));
        }

        return Optional.ofNullable(accessor);
    return null;
  }

  @Override
  public List<DynamicTest> getMethodDynamicTest(Method method) {
    // TODO Auto-generated method stub
    return null;
  }

  public AutoGetterUnitTest(Field field, BeanUnitTestConfig configuration) {
    this.field = field;
    this.configuration = configuration;
  }
}
