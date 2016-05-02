package ua.skillsup.demitt.reflection.service;

import ua.skillsup.demitt.reflection.annotation.CustomDateFormat;
import ua.skillsup.demitt.reflection.annotation.JsonValue;
import ua.skillsup.demitt.reflection.data.Data;
import ua.skillsup.demitt.reflection.data.Entity;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Jsoner {

    /*Получение JSON-представления полей экземпляра класса.
    В случае ошибки вернет null.
    */
    public static String toJson(Object obj) {
        Class clazz = obj.getClass();
        List<Entity> dataList = new ArrayList<>();

        dataList.add( new Entity(Data.DESCRIBE_VARNAME, clazz.getName())  );
        String fieldNameRaw, fieldName;
        String fieldValueString = null;
        Object fieldValueRaw;
        boolean isNotAccessible;
        for (Field field : clazz.getDeclaredFields()) {
            isNotAccessible = false;

            //Получаем имя поля:

            fieldNameRaw = field.getName();
            if ( customNameNeed(field) ) {
                fieldName = field.getAnnotation(JsonValue.class).value();
            } else {
                fieldName = fieldNameRaw;
            }

            //Получаем тип и значение поля:

            if (!field.isAccessible()) {
                field.setAccessible(true);
                isNotAccessible = true;
            }

            try {
                if (field.get(obj) == null) {
                    continue;
                }
                fieldValueRaw = field.get(obj);
            }
            catch (IllegalAccessException e) {
                System.out.println("Произошла ошибка чтения поля" + fieldNameRaw);
                e.printStackTrace();
                return null;
            }
            fieldValueString = fieldValueRaw.toString();

            //Проверим, не требуется ли нам некое особое форматирование даты:

            if ( customDateFormatNeed(field) ) { //да, требуется
                //Это именно LocalDate?
                if ( field.getType() != LocalDate.class ) {
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

            //Убираем за собой: закрываем доступ к полю (если он был):
            if (isNotAccessible) {
                field.setAccessible(false);
            }

            dataList.add( new Entity(fieldName, fieldValueString) );
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

    private static boolean customDateFormatNeed(Field field) {
        return field.isAnnotationPresent(CustomDateFormat.class);
    }

    private static boolean customNameNeed(Field field) {
        return field.isAnnotationPresent(JsonValue.class);
    }

}
