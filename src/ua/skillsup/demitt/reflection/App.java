package ua.skillsup.demitt.reflection;

import ua.skillsup.demitt.reflection.laboratory.User;
import ua.skillsup.demitt.reflection.service.Jsoner;
import ua.skillsup.demitt.reflection.io.Storage;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        System.out.println("Reflection API.");

        User obj = new User(null, "Дибенко", LocalDate.of(1980, 03, 23), "Человек-без-лица", LocalDate.of(2012, 4, 16), "Ubuntu 12.04", 42, 100);

        String jsonString = Jsoner.toJson(obj);
        if (jsonString == null) {
            System.out.println("Паникуем и прекращаем работу.");
            return;
        }
        System.out.println("Прочли поля класса в JSON:");
        System.out.println(jsonString);

        boolean writeResult = Storage.writeFile(jsonString);
        if (!writeResult) {
            System.out.println("Записать в файл не удалось. Печаль.");
            return;
        }
        System.out.println("Записали данные в файл.");

        //TODO...
        //String readedJsonString = Storage.readFile();



    }
}
