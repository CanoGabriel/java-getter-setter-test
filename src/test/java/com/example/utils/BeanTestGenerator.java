package com.example.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.DynamicTest;

public class BeanTestGenerator implements CustomTypeRegisterer {
    Map<Class<?>, ICustomTypeHandler> customTypeConfig = new HashMap<>();

    private String generateGetterName(String fieldName) {
        return String.format("get%s%s", fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));
    }

    private String generateSetterName(String fieldName) {
        return String.format("set%s%s", fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));
    }

    private Optional<Method> extractOptAccessor(Class<?> objClass, Class<?> fieldClass, String methodName,
            boolean isSetter) {
        Method accessor = null;

        try {
            accessor = isSetter ? objClass.getDeclaredMethod(methodName, fieldClass)
                    : objClass.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException noSuchMethodException) {
            System.out.println(String.format("No method %s declared", methodName));
        }

        return Optional.ofNullable(accessor);
    }

    private String generateGetterTestName(Field field) {
        String fieldName = field.getName();
        String getterName = generateGetterName(fieldName);
        return String.format("Getter %s must get %s field", getterName, fieldName);
    }

    private String generateSetterTestName(Field field) {
        String fieldName = field.getName();
        String getterName = generateSetterName(fieldName);
        return String.format("Setter %s must set %s field", getterName, fieldName);
    }

    private Collection<DynamicTest> generateFieldUnitTest(Class<?> objClass, Field field, Object instance, Map<Class<?>, ICustomTypeHandler> customTypeConfig) {
        Collection<DynamicTest> result = new ArrayList<>();
        if (!field.canAccess(instance)) {
            field.setAccessible(true);
        }

        Class<?> fieldClazz = field.getType();
        String fieldName = field.getName();
        Optional<Method> OptGetter = extractOptAccessor(objClass, fieldClazz, generateGetterName(fieldName), false);
        Optional<Method> OptSetter = extractOptAccessor(objClass, fieldClazz, generateSetterName(fieldName), true);

        OptGetter.ifPresent((Method getter) -> {
            GetterUnitTest getterUnitTest = new GetterUnitTest(field, getter, instance);
            for(Map.Entry<Class<?>, ICustomTypeHandler> entry : customTypeConfig.entrySet() ){
                System.out.println("register " + entry.getKey());
                getterUnitTest.registerSupportedType(entry.getKey(), entry.getValue());
            }
            DynamicTest dynamicTest = DynamicTest.dynamicTest(generateGetterTestName(field), getterUnitTest);
            result.add(dynamicTest);
        });

        OptSetter.ifPresent((Method setter) -> {
            SetterUnitTest setterUnitTest = new SetterUnitTest(field, setter, instance);

            for(Map.Entry<Class<?>, ICustomTypeHandler> entry : customTypeConfig.entrySet() ){
                setterUnitTest.registerSupportedType(entry.getKey(), entry.getValue());
            }

            DynamicTest dynamicTest = DynamicTest.dynamicTest(generateSetterTestName(field), setterUnitTest);
            result.add(dynamicTest);
        });

        return result;
    }

    public Collection<DynamicTest> generateClassTest(Class<?> objClass, Object instance) {

        List<Field> declaredFieldList = Arrays.asList(objClass.getDeclaredFields());
        List<DynamicTest> result = new ArrayList<>();

        for (Field field : declaredFieldList) {
            result.addAll(generateFieldUnitTest(objClass, field, instance, customTypeConfig));
        }
        return result;
    }

    @Override
    public void registerSupportedType(Class<?> clazz, ICustomTypeHandler config) {
        customTypeConfig.put(clazz, config);
    }

}
