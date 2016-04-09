package ua.skillsup.demitt.reflection.service;

import ua.skillsup.demitt.reflection.annotation.CustomDateFormat;
import ua.skillsup.demitt.reflection.annotation.JsonValue;
import ua.skillsup.demitt.reflection.data.Data;
import ua.skillsup.demitt.reflection.data.DataType;
import ua.skillsup.demitt.reflection.data.Entity;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Jsoner {

    /*Получение JSON-представления полей экземплчра класса.
    В случае ошибки чтения вернет null.
    */
    public static String toJson(Object obj) {
        Class clazz = obj.getClass();
        List<Entity> dataList = new ArrayList<>();

        dataList.add( new Entity(Data.DESCRIBE_VARNAME, DataType.STRING, clazz.getName())  );
        String fieldNameRaw, fieldName, fieldTypeString, fieldValueString = null;
        Object fieldValueRaw;
        boolean typeIsFound, isNotAccessible;
        DataType dataType = null;
        nextField:
        for (Field field : clazz.getDeclaredFields()) {
            isNotAccessible = false;

            //Получаем имя поля:
            //TODO поместить после получения значения!
            fieldNameRaw = field.getName();
            if (field.isAnnotationPresent(JsonValue.class)) {
                fieldName = field.getAnnotation(JsonValue.class).value();
            } else {
                fieldName = fieldNameRaw;
            }
            //System.out.println("Подтюненое имя поля: " + fieldName);

            //Получаем тип и значение поля:

            fieldTypeString = field.getType().getSimpleName();

            if (!field.isAccessible()) {
                field.setAccessible(true);
                isNotAccessible = true;
            }

            typeIsFound = false;

            //TODO вынести в метод?
            for (DataType currDataType : DataType.values()) { //по всем эл-там перечисления (а это список типов)
                if (fieldTypeString.equals(currDataType.getType())) { //да, это наш тип
                    dataType = currDataType;
                    typeIsFound = true;

                    //Получаем тип:
                    /*Тип уже находится в переменной fieldTypeString.
                    А вот если бы мы для маркировки типа использовали не его джавовское написание, а некое свое
                    (например, для типа int использовали бы не строку "int", а строку "целое примитивное"),
                    то здесь бы мы вызвали метод, возвращающий эту строку.*/

                    //Получаем значение:
                    try {
                        if (/*currDataType.canBeNull() &&*/ field.get(obj) == null) { //может быть null и равен null
                            continue nextField;
                        }
                        fieldValueRaw = field.get(obj);
                        fieldValueString = fieldValueRaw.toString();
                    }
                    catch (IllegalAccessException e) {
                        System.out.println("Произошла ошибка чтения поля" + fieldNameRaw);
                        e.printStackTrace();
                        return null;
                    }

                    //Проверим, не требуется ли нам некое особое форматирование даты:
                    if (field.isAnnotationPresent(CustomDateFormat.class)) { //да, есть такая аннотация
                        //Проверим, чтобы это было именно DataType.LOCALDATE:
                        if (!fieldTypeString.equals(DataType.LOCALDATE.getType())) {
                            System.out.println("Недопустимое использование аннотации " + CustomDateFormat.class.getSimpleName() + " вместе с полем " + fieldNameRaw);
                            return null;
                        }
                        String dateFormatCustom = field.getAnnotation(CustomDateFormat.class).format();
                        try {
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormatCustom);
                            fieldValueString = ((LocalDate)fieldValueRaw).format(dateFormatter);
                        }
                        catch (DateTimeParseException | IllegalArgumentException e) {
                            System.out.println("Для поля " + fieldNameRaw + " указан неверный формат даты: " + dateFormatCustom);
                            return null;
                        }
                    }

                }
            }

            //Если тип поля не найден в нашем списке:
            if (!typeIsFound) {
                System.out.println("Тип поля не найден в списке");
                return null;
            }

            //Убираем за собой: закрываем доступ к полю (если он был):
            if (isNotAccessible) {
                field.setAccessible(false);
            }

            dataList.add( new Entity(fieldName, dataType, fieldValueString) );
        }


        return getObjectString(dataList);
    }

    private static String getObjectString(List<Entity> dataList) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        int length = dataList.size();
        for (int i = 0; i < length; i++) {
            sb.append("    ");
            sb.append( dataList.get(i).getEntity() );
            if (i != length-1) {
                sb.append(",\n");
            }
        }
        sb.append("\n}");
        return sb.toString();
    }

}
