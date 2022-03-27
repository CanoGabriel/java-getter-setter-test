package com.example.bean;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.example.utils.BeanTestGenerator;
import com.example.utils.ICustomTypeHandler;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class DataTest {

    private static Map<Class<?>, ICustomTypeHandler> CUSTOM_SUPPORTED_TYPE;
    static {
        CUSTOM_SUPPORTED_TYPE = new HashMap<>();
        CUSTOM_SUPPORTED_TYPE.put(String.class, new ICustomTypeHandler() {
            @Override
            public Object getDefaultInstance() {
                return "";
            }
        });

        CUSTOM_SUPPORTED_TYPE.put(LocalDate.class, new ICustomTypeHandler() {
            @Override
            public Object getDefaultInstance() {
                return LocalDate.of(2022, 1, 1);
            }
        });
    }

    @TestFactory
    public Collection<DynamicTest> generateBeanUnitTests() {
        BeanTestGenerator beanTestGenerator = new BeanTestGenerator();
        for (Map.Entry<Class<?>, ICustomTypeHandler> entry : CUSTOM_SUPPORTED_TYPE.entrySet()) {
            beanTestGenerator.registerSupportedType(entry.getKey(), entry.getValue());
        }

        Data data = new Data();
        Collection<DynamicTest> dataBeanUnitTests = beanTestGenerator.generateClassTest(Data.class, data);
        return dataBeanUnitTests;
    }

}
