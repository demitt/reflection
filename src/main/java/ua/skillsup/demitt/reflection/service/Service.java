package ua.skillsup.demitt.reflection.service;

import ua.skillsup.demitt.reflection.annotation.CustomDateFormat;
import ua.skillsup.demitt.reflection.annotation.JsonValue;
import ua.skillsup.demitt.reflection.data.Data;
import ua.skillsup.demitt.reflection.data.Entity;
import ua.skillsup.demitt.reflection.io.Storage;
import ua.skillsup.demitt.reflection.laboratory.User;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Service {

    public static void start() {

        System.out.println("Reflection API.");

        User obj = new User(null, "Дибенко", LocalDate.of(1980, 3, 23), "Человек-без-лица", LocalDate.of(2012, 4, 16), "Ubuntu 12.04", 42, 100);

        String jsonString = Service.objectToJson(obj);
        if (jsonString == null) {
            System.out.println("Паникуем и прекращаем работу.");
            return;
        }
        System.out.println("Прочли поля класса в JSON:");
        System.out.println(jsonString);

        boolean writeResult = Storage.writeFile(jsonString);
        if (!writeResult) {
            System.out.println("Записать в файл не удалось. Печаль.");
            return;
        }
        System.out.println("Записали данные в файл.");

        //TODO
        //String readedJsonString = Storage.readFile();
    }


    /*Получение JSON-представления полей экземпляра класса.
    В случае ошибки вернет null.
    */
    public static String objectToJson(Object obj) {
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
            if ( isCustomFieldNameNeed(field) ) {
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

            if ( isCustomDateFormatNeed(field) ) { //да, требуется
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

        return getObjectJsonString(dataList);
    }


    private static String getObjectJsonString(List<Entity> dataList) {
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


    private static boolean isCustomDateFormatNeed(Field field) {
        return field.isAnnotationPresent(CustomDateFormat.class);
    }


    private static boolean isCustomFieldNameNeed(Field field) {
        return field.isAnnotationPresent(JsonValue.class);
    }

}
