package main.file;

import main.contracts.Validate;
import main.enums.FileMessages;
import main.exeptions.FileException;
import main.exeptions.ValidateException;
import main.singletons.FileData;
import main.validate.JsonValidator;

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

        try {
        Validate jsonValidator=new JsonValidator(fileContent.toString());
         jsonValidator.validate();

        } catch (ValidateException ve)
        {
         System.out.println(ve.getMessage());
         error(FileMessages.INCORRECT_VALIDATION);
        }
        return fileContent.toString();
    }
    private void error(FileMessages fileMessages) throws FileException {
        throw new FileException(fileMessages);
    }
}


