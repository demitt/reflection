package ua.skillsup.demitt.reflection.io;

import org.junit.Assert;
import org.junit.Test;
import ua.skillsup.demitt.reflection.data.Literal;

import java.io.File;

public class StorageTest {

    @Test
    public void testWriteFile_nullableArgument() throws Exception {
        //Given
        //...
        //When
        boolean writeResult = Storage.writeFile(null);
        //Then
        Assert.assertFalse("В качестве строки для записи в файл передан null", writeResult);
    }

    @Test
    public void testWriteFile_getIOException() throws Exception {
        //Given
        File file = new File(Literal.DATA_FILE);
        boolean result = file.createNewFile();
        result = file.setWritable(false);
        //When
        boolean writeResult = Storage.writeFile("someString");
        //Then
        Assert.assertFalse("Запрет на запись в файл", writeResult);
        //Clean
        result = file.delete();
    }
}