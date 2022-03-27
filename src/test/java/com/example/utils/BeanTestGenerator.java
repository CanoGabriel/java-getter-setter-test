package com.example.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.example.utils.runner.AutoGetterUnitTest;
import com.example.utils.runner.AutoSetterUnitTest;

import org.junit.jupiter.api.DynamicTest;

public class BeanTestGenerator {
    BeanUnitTestConfig configuration = new BeanUnitTestConfig();

    public BeanUnitTestConfig getConfiguration() {
        return configuration;
    }

    public void setConfiguration(BeanUnitTestConfig configuration) {
        this.configuration = configuration;
    }

    public Collection<DynamicTest> generateClassTest(Class<?> objClass, Object instance) {

        List<Field> declaredFieldList = Arrays.asList(objClass.getDeclaredFields());
        List<DynamicTest> result = new ArrayList<>();
        for (Field field : declaredFieldList) {
            AutoGetterUnitTest autoGetterUnitTest = new AutoGetterUnitTest(field, configuration);
            AutoSetterUnitTest autoSetterUnitTest = new AutoSetterUnitTest(field, configuration);
            
            // TODO make it configurable (register test template by class type instance)
            result.addAll(autoGetterUnitTest.generateTestSuite(instance));
            result.addAll(autoSetterUnitTest.generateTestSuite(instance));
        }

        return result;
    }
}
