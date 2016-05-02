package ua.skillsup.demitt.reflection.data;

public class Entity {
    private String name;
    private String value;

    public Entity(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getEntity() {
        return "\"" + this.name + "\": \"" + this.value + "\"";
    }

}
