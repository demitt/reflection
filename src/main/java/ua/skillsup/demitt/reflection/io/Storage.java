package ua.skillsup.demitt.reflection.io;

import ua.skillsup.demitt.reflection.data.Data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Storage {

    public static boolean writeFile(String dataString) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Data.DATA_FILE))) {
            bw.write(dataString);
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;

        }
        return true;
    }


    //TODO
    public static String readFile() {
        //
        return "";
    }

}
