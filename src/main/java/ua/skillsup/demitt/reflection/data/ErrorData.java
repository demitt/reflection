package ua.skillsup.demitt.reflection.data;

public enum ErrorData {
    ILLEGAL_ANNO_USAGE("недопустимое использование аннотации"),
    INVALID_DATE_FORMAT("указан неверный формат даты")
    ;

    private String message;

    ErrorData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
