package com.example.bean;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.example.utils.BeanTestGenerator;
import com.example.utils.BeanUnitTestConfig;
import com.example.utils.handler.ICustomTypeHandler;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class DataTest {

    @TestFactory
    public Collection<DynamicTest> generateBeanUnitTests() {
        BeanTestGenerator beanTestGenerator = new BeanTestGenerator();
        

        Data data = new Data();
        Collection<DynamicTest> dataBeanUnitTests = beanTestGenerator.generateClassTest(Data.class, data);
        return dataBeanUnitTests;
    }

}
