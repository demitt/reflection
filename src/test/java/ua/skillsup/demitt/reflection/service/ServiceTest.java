package ua.skillsup.demitt.reflection.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ua.skillsup.demitt.reflection.service.laboratory.User_IllegalAnnoUse;
import ua.skillsup.demitt.reflection.service.laboratory.User_IllegalDateFormat;
import ua.skillsup.demitt.reflection.service.laboratory.User_EmptyFieldName;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;

public class ServiceTest {

    private Object expectedOnError = null;

    /*@Before
    public void setUp() throws Exception {

    }*/

    /*@After
    public void tearDown() throws Exception {

    }*/

    /*@Test
    public void testStart() throws Exception {

    }*/

    @Test
    public void testGetCustomFormattedDate_illeganAnnoUse() throws Exception {
        //Given
        User_IllegalAnnoUse obj = new User_IllegalAnnoUse("myLogin");
        Field field = obj.getClass().getDeclaredField("login");
        field.setAccessible(true);
        Object value = field.get(obj);
        Method m = Service.class.getDeclaredMethod("getCustomFormattedDate", Field.class, Object.class);
        m.setAccessible(true);
        //When
        Object resultObj = m.invoke(obj, field, value);
        //Then
        Assert.assertEquals("CustomDateFormat был повешен на НЕ LocalDate", expectedOnError, resultObj);
    }

    @Test
    @Ignore("TODO: признать формат даты невалидным, если в нем нет минимального набора спецсимволов. Видимо, только регуляркой.")
    public void testGetCustomFormattedDate_illegalDateFormat() throws Exception {
        //Given
        User_IllegalDateFormat obj = new User_IllegalDateFormat(LocalDate.of(1980, 10, 25));
        Field field = obj.getClass().getDeclaredField("dateFrom");
        field.setAccessible(true);
        Object value = field.get(obj);
        Method m = Service.class.getDeclaredMethod("getCustomFormattedDate", Field.class, Object.class);
        m.setAccessible(true);
        //When
        Object resultObj = m.invoke(obj, field, value);
        //Then
        Assert.assertEquals("Указан неправильный кастомный формат даты", expectedOnError, resultObj);
    }

    @Test
    public void testObjectToJson_EmptyCustomFieldName() throws Exception {
        //Given
        User_EmptyFieldName obj = new User_EmptyFieldName("Ubuntu 12.04 LTS");
        //When
        String resultString = Service.objectToJson(obj);
        //Then
        Assert.assertNotEquals("Пустая строка в кастомном имени поля - вернуло null!", expectedOnError, resultString);
    }

}