package ua.skillsup.demitt.reflection.laboratory;

/*
Подопытный класс.
*/

import ua.skillsup.demitt.reflection.annotation.CustomDateFormat;
import ua.skillsup.demitt.reflection.annotation.JsonValue;

import java.time.LocalDate;

public class User {
    private String firstName;
    private String lastName;
    private String login;
    private LocalDate birthDate;
    @CustomDateFormat(format="yyyy.MM.dd")
    private LocalDate dateFrom;
    @JsonValue(value="OS")
    private String operationSystem;
    @JsonValue(value="userLevel")
    private int skillsLevel;
    @JsonValue(value="pcLevel")
    private int computerPowerLevel;

    public User(String firstName, String lastName, LocalDate birthDate, String login, LocalDate dateFrom, String operationSystem, int skillsLevel, int computerPowerLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.login = login;
        this.dateFrom = dateFrom;
        this.operationSystem = operationSystem;
        this.skillsLevel = skillsLevel;
        this.computerPowerLevel = computerPowerLevel;
    }

    //Где-то здесь могут быть геттеры, сеттеры и прочие методы...
}
