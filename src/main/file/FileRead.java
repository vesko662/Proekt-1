package main.file;

import main.enums.FileMessages;
import main.exceptions.FileException;
import main.singletons.FileData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileRead {

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public String readFile(String filePath) throws FileException {
        if (FileData.getInstance().isFileOpen())
        {
            error(FileMessages.FILE_OPEN);
        }


        if (!filePath.toLowerCase().endsWith(".json")) {
            error(FileMessages.WRONG_EXTENSION);
        }


        StringBuilder fileContent=new StringBuilder();

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                if(!file.createNewFile()) {
                    error(FileMessages.UNSUCCESSFUL_CREATED_FILE);
                }
                else {
                    return "{ }";
                }
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append(System.lineSeparator());
                }
            }
            fileName=file.getName();
        } catch (IOException e) {
            error(FileMessages.ERROR_WHEN_READING_FILE);
        }

        return fileContent.toString();
    }
    private void error(FileMessages fileMessages) throws FileException {
        throw new FileException(fileMessages);
    }
}


