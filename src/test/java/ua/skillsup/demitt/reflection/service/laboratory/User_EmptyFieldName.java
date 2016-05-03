package ua.skillsup.demitt.reflection.service.laboratory;

import ua.skillsup.demitt.reflection.annotation.JsonValue;

import java.time.LocalDate;

public class User_EmptyFieldName {
    @JsonValue(value="") //пустая строка в кастомном имени поля
    private String operationSystem;

    public User_EmptyFieldName(String operationSystem) {
        this.operationSystem = operationSystem;
    }

}
