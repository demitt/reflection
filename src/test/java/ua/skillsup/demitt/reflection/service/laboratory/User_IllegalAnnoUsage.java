package ua.skillsup.demitt.reflection.service.laboratory;

import ua.skillsup.demitt.reflection.annotation.CustomDateFormat;

import java.time.LocalDate;

public class User_IllegalAnnoUsage {

    private String firstName;
    @CustomDateFormat(format="yyyy.MM.dd") //применили к полю не того типа
    private String login;

    public User_IllegalAnnoUsage(String login) {
        this.login = login;
    }

}
