package ua.skillsup.demitt.reflection.io;

import ua.skillsup.demitt.reflection.data.Literal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Storage {

    public static boolean writeFile(String dataString) {
        if (dataString == null) {
            return false;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Literal.DATA_FILE))) {
            bw.write(dataString);
        }
        catch (IOException e) {
            //e.printStackTrace();
            return false;

        }
        return true;
    }

}
