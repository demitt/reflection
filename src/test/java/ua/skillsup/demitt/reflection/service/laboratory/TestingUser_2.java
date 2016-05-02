package ua.skillsup.demitt.reflection.service.laboratory;

import ua.skillsup.demitt.reflection.annotation.CustomDateFormat;

import java.time.LocalDate;

public class TestingUser_2 {
    @CustomDateFormat(format="фОрМаТиК") //неправильный формат даты
    private LocalDate dateFrom;

    public TestingUser_2(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

}
