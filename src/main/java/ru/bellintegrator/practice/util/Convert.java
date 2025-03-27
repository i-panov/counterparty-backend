package ru.bellintegrator.practice.util;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;

public class Convert {
    public static <T> T tableToObject(Map<String, Object> table, Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        T value = clazz.newInstance();

        for (Field field : clazz.getFields()) {
            Class<?> fieldClass = field.getType();

            if (Iterable.class.isAssignableFrom(fieldClass))
                continue;
            else if (!isConvertible(fieldClass))
            {
                String name = String.format("%s_id", field.getName());
                Object id = table.get(name);

                if (id == null)
                    continue;

                Object fieldValue = fieldClass.newInstance();
                Field idField = fieldClass.getField("id");
                idField.set(fieldValue, changeType(id, idField.getType()));
                field.set(value, fieldValue);
            }
            else {
                Object fieldValue = table.get(field.getName());

                if (fieldValue == null)
                    continue;

                field.set(value, changeType(fieldValue, fieldClass));
            }
        }

        return value;
    }

    public static Map<String, Object> objectToTable(Object value) throws IllegalAccessException, NoSuchFieldException {
        Class clazz = value.getClass();
        Map<String, Object> table = new HashMap<>();

        for (Field field : clazz.getFields()) {
            Class<?> fieldClass = field.getType();

            if (Iterable.class.isAssignableFrom(fieldClass))
                continue;
            else if (!isConvertible(fieldClass))
            {
                String name = String.format("%s_id", field.getName());
                Object id = fieldClass.getField("id").get(field.get(value));
                table.put(name, id);
            }
            else
                table.put(field.getName(), field.get(value));
        }

        return table;
    }

    @SuppressWarnings("unchecked")
    public static <T> T changeType(Object value, Class<T> toClass) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(toClass, "toClass");

        Class<?> fromClass = value.getClass();

        if (toClass.equals(fromClass))
            return (T)value;

        if (!isConvertible(fromClass))
            throw new IllegalArgumentException("fromClass");

        if (!isConvertible(toClass))
            throw new IllegalArgumentException("toClass");

        if (isNumber(fromClass)) {
            Number nValue = (Number)value;

            if (isNumber(toClass))
                return (T)toNumber(nValue, (Class<? extends Number>)toClass);
            else if (isBool(toClass))
                return (T)(Boolean)(nValue.byteValue() != 0);
            else if (isChar(toClass))
                return (T)(Character)Character.forDigit(nValue.intValue(), 10);
            else if (toClass.equals(String.class))
                return (T)nValue.toString();
        } else if (isBool(fromClass)) {
            Boolean bValue = (Boolean)value;

            if (isNumber(toClass))
                return (T)toNumber(bValue ? 1 : 0, (Class<? extends Number>)toClass);
            else if (isBool(toClass))
                return (T)bValue;
            else if (isChar(toClass))
                return (T)(Character)(bValue ? '1' : '0');
            else if (toClass.equals(String.class))
                return (T)bValue.toString();
        } else if (isChar(fromClass)) {
            Character cValue = (Character)value;

            if (isNumber(toClass))
                return (T)(Number)(int)cValue;
            else if (isBool(toClass))
                return (T)(Boolean)(cValue != '1');
            else if (isChar(toClass))
                return (T)cValue;
            else if (toClass.equals(String.class))
                return (T)cValue.toString();
        } else if (fromClass.equals(String.class)) {
            String sValue = (String)value;

            if (isNumber(toClass))
                return (T)toNumber(sValue, (Class<? extends Number>)toClass);
            else if (isBool(toClass))
                return (T)Boolean.valueOf(sValue);
            else if (isChar(toClass)) {
                if (sValue.length() != 1)
                    throw new IllegalArgumentException("value");

                return (T)(Character)sValue.charAt(0);
            }
            else if (toClass.equals(String.class))
                return (T)sValue;
        }

        throw new IllegalArgumentException();
    }

    private static boolean isConvertible(Class<?> clazz) {
        return clazz.isPrimitive() || Number.class.isAssignableFrom(clazz) || clazz.equals(Boolean.class) || clazz.equals(Character.class) || clazz.equals(String.class);
    }

    private static boolean isNumber(Class<?> clazz) {
        return (clazz.isPrimitive() && !clazz.equals(boolean.class) && !clazz.equals(char.class)) || Number.class.isAssignableFrom(clazz);
    }

    private static boolean isBool(Class<?> clazz) {
        return clazz.equals(boolean.class) || clazz.equals(Boolean.class);
    }

    private static boolean isChar(Class<?> clazz) {
        return clazz.equals(char.class) || clazz.equals(Character.class);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Number> T toNumber(String value, Class<T> clazz) {
        if (clazz.equals(byte.class) || clazz.equals(Byte.class))
            return (T)Byte.valueOf(value);
        else if (clazz.equals(short.class) || clazz.equals(Short.class))
            return (T)Short.valueOf(value);
        else if (clazz.equals(int.class) || clazz.equals(Integer.class))
            return (T)Integer.valueOf(value);
        else if (clazz.equals(long.class) || clazz.equals(Long.class))
            return (T)Long.valueOf(value);
        else if (clazz.equals(float.class) || clazz.equals(Float.class))
            return (T)Float.valueOf(value);
        else if (clazz.equals(double.class) || clazz.equals(Double.class))
            return (T)Double.valueOf(value);

        throw new IllegalArgumentException();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Number> T toNumber(Number value, Class<T> toClass) {
        Class<?> fromClass = value.getClass();

        if (toClass.equals(fromClass))
            return (T)value;
        else if (toClass.equals(byte.class) || toClass.equals(Byte.class))
            return (T)(Number)value.byteValue();
        else if (toClass.equals(short.class) || toClass.equals(Short.class))
            return (T)(Number)value.shortValue();
        else if (toClass.equals(int.class) || toClass.equals(Integer.class))
            return (T)(Number)value.intValue();
        else if (toClass.equals(long.class) || toClass.equals(Long.class))
            return (T)(Number)value.longValue();
        else if (toClass.equals(float.class) || toClass.equals(Float.class))
            return (T)(Number)value.floatValue();
        else if (toClass.equals(double.class) || toClass.equals(Double.class))
            return (T)(Number)value.doubleValue();

        throw new IllegalArgumentException();
    }
}
