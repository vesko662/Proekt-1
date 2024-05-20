package main.file;

import main.enums.FileMessages;
import main.exceptions.FileException;

import java.io.*;

public class FileWrite {

    public void writeFile(String filePath,String content) throws FileException {
        if (!filePath.toLowerCase().endsWith(".json")) {
            error(FileMessages.WRONG_EXTENSION);
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(content);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private void error(FileMessages fileMessages) throws FileException {
        throw new FileException(fileMessages);
    }
}
