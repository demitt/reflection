package ua.skillsup.demitt.reflection.data;

/**
 * Created by demitt on 09.04.16.
 */
public class Entity {
    private String name;
    private DataType type;
    private String value;

    public Entity(String name, DataType type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getEntity() {
        return "\"" + this.name + "\": [\"" + this.type.getType() + "\", \"" + this.value + "\"]";
    }

}
