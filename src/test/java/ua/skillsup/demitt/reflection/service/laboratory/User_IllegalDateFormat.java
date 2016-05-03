package ua.skillsup.demitt.reflection.service.laboratory;

import ua.skillsup.demitt.reflection.annotation.CustomDateFormat;

import java.time.LocalDate;

public class User_IllegalDateFormat {
    @CustomDateFormat(format="фОрМаТиК") //неправильный формат даты
    private LocalDate dateFrom;

    public User_IllegalDateFormat(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

}
