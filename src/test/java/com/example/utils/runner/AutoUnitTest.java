package com.example.utils.runner;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.DynamicTest;

public abstract class AutoUnitTest {
  public abstract String getTestName();
  public abstract Method getMethodToTest(Object instance);
  public abstract List<DynamicTest> getMethodDynamicTestSuite(Object instance, Method method);


  public Collection<DynamicTest> generateTestSuite(Object instance){
    Method methodToTest = getMethodToTest(instance);
    return getMethodDynamicTestSuite(instance, methodToTest);
  }
}
