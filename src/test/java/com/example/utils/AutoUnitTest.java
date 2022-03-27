package com.example.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.DynamicTest;

public abstract class AutoUnitTest {
  public abstract String getTestName();
  public abstract List<Method> getMethodToTest(Object instance);
  public abstract List<DynamicTest> getMethodDynamicTest(Method method);


  public Collection<DynamicTest> generateTestSuite(Object instance){

    List<Method> methodToTest = getMethodToTest(instance);
    List<DynamicTest> result = new ArrayList<>();

        for (Method method : methodToTest) {
            result.addAll(getMethodDynamicTest(method));
        }
        return result;
  }
}
