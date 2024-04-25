package main.file;

import main.contracts.Validate;
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

    public String readFile(String filePath)
    {
        StringBuilder fileContent=new StringBuilder();

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                if(file.createNewFile()) {
                    //TODO end
                } else {
                    //TODO Error for not successful creation
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
            //TODO Error handler
        }
        Validate jsonValidator=new JsonValidator(fileContent.toString());

        boolean isValid=false;
        try {
            isValid =  jsonValidator.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!isValid)
        {
            //TODO Error handler
        }
        return fileContent.toString();
    }

}


