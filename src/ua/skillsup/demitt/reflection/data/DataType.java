package ua.skillsup.demitt.reflection.data;


public enum DataType {

    STRING("String" /*, true*/ ),
    INT("int" /*, false*/ ),
    LOCALDATE("LocalDate" /*, true*/ )
    //TODO при необходимости добавить еще типы
    ;

    private String type; //строковое описание типа данных
    //private boolean canBeNull; //флаг того, может ли тип данных принимать значение null

    DataType(String type /*, boolean canBeNull*/) {
        this.type = type;
        //this.canBeNull = canBeNull;
    }

    public String getType() {
        return this.type;
    }

    /*public boolean canBeNull() {
        return this.canBeNull;
    }*/
}
