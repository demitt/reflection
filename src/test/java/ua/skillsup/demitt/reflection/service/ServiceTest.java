package ua.skillsup.demitt.reflection.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ua.skillsup.demitt.reflection.service.laboratory.TestingUser_1;
import ua.skillsup.demitt.reflection.service.laboratory.TestingUser_2;
import ua.skillsup.demitt.reflection.service.laboratory.TestingUser_3;

import java.lang.reflect.Field;
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
    public void testObjectToJson_InvalidAnno1() throws Exception {
        //Given
        //...
        //When
        TestingUser_1 obj = new TestingUser_1("login");
        Object result = Service.objectToJson(obj);
        /*
        Может быть, здесь и в следующем тесте надо вызывать рефлексией приватный метод getCustomFormattedDate()
        и скармливать ему только одно поле (также аолученное рефлексией)?
        В этом случае мы будем тестить именно *его* внутренности.
         */
        //Then
        Assert.assertEquals("CustomDateFormat повешен НЕ на LocalDate", this.expectedOnError, result);
    }

    @Test
    @Ignore("TODO: признать формат даты невалидным, если в нем нет минимального набора спецсимволов. Видимо, только регуляркой.")
    public void testObjectToJson_InvalidAnno2() throws Exception {
        //Given
        //...
        //When
        TestingUser_2 obj = new TestingUser_2(LocalDate.of(1980, 10, 25));
        Object result = Service.objectToJson(obj);
        //Then
        Assert.assertEquals("Указан неправильный кастомный формат даты", this.expectedOnError, result);

    }

    @Test
    public void testObjectToJson_EmptyCustomFieldName() throws Exception {
        //Given
        //...
        //When
        TestingUser_3 obj = new TestingUser_3("Ubuntu 12.04 LTS");
        String resultString = Service.objectToJson(obj);
        //Then
        Assert.assertNotEquals("Пустая строка в кастомном имени поля - вернуло null!", expectedOnError, resultString);
    }

    /*@Test
    public void testIsCustomDateFormatNeed() throws Exception {
        //Given
        Field field = new Field(LocalDate.class, "someField", LocalDate.class); // :-(
    }*/

}