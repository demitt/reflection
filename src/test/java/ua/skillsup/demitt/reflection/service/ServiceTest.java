package ua.skillsup.demitt.reflection.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ua.skillsup.demitt.reflection.data.ErrorData;
import ua.skillsup.demitt.reflection.service.laboratory.User_IllegalAnnoUsage;
import ua.skillsup.demitt.reflection.service.laboratory.User_IllegalDateFormat;
import ua.skillsup.demitt.reflection.service.laboratory.User_EmptyFieldName;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;

public class ServiceTest {

    @Test
    //Экперимент: тестируем приватный метод
    public void testGetCustomFormattedDate_illeganAnnoUsage() throws Exception {
        //Given
        ErrorData expected = ErrorData.ILLEGAL_ANNO_USAGE;
        //When
        User_IllegalAnnoUsage obj = new User_IllegalAnnoUsage("myLogin");
        Field field = obj.getClass().getDeclaredField("login");
        field.setAccessible(true);
        Object value = field.get(obj);
        Method m = Service.class.getDeclaredMethod("getCustomFormattedDate", Field.class, Object.class);
        m.setAccessible(true);
        Service.AnswerData answer = (Service.AnswerData) m.invoke(obj, field, value);
        //Then
        Assert.assertEquals("CustomDateFormat был повешен НЕ на LocalDate", expected, answer.getErrorData());
    }

    @Test
    //Экперимент: тестируем приватный метод
    @Ignore("TODO: признать формат даты невалидным, если в нем нет минимального набора спецсимволов. " +
        "Видимо, такая проверка возможна только регуляркой.")
    public void testGetCustomFormattedDate_illegalDateFormat() throws Exception {
        //Given
        ErrorData expected = ErrorData.INVALID_DATE_FORMAT;
        //When
        User_IllegalDateFormat obj = new User_IllegalDateFormat(LocalDate.of(1980, 10, 25));
        Field field = obj.getClass().getDeclaredField("dateFrom");
        field.setAccessible(true);
        Object value = field.get(obj);
        Method m = Service.class.getDeclaredMethod("getCustomFormattedDate", Field.class, Object.class);
        m.setAccessible(true);
        Service.AnswerData answer = (Service.AnswerData) m.invoke(obj, field, value);
        //Then
        Assert.assertEquals("Указан неправильный кастомный формат даты", expected, answer.getErrorData());
    }

    @Test
    public void testObjectToJson_EmptyCustomFieldName() throws Exception {
        //Given
        //...
        //When
        User_EmptyFieldName obj = new User_EmptyFieldName("Ubuntu 12.04 LTS");
        String resultString = Service.objectToJson(obj);
        //Then
        Assert.assertNotEquals("Пустая строка в кастомном имени поля - вернуло null!", null, resultString);
    }

}