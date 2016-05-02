package ua.skillsup.demitt.reflection.service.laboratory;

import ua.skillsup.demitt.reflection.annotation.JsonValue;

import java.time.LocalDate;

public class TestingUser_3 {
    @JsonValue(value="") //пустая строка в кастомном имени поля
    private String operationSystem;

    public TestingUser_3(String operationSystem) {
        this.operationSystem = operationSystem;
    }

}
