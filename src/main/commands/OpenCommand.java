package main.commands;

import main.contracts.JSON;
import main.exceptions.FileException;
import main.exceptions.ValidateException;
import main.file.FileRead;
import main.contracts.Command;
import main.parcer.JSONParser;
import main.singletons.FileData;

public class OpenCommand implements Command {
    @Override
    public JSON execute(String args, JSON j) {
        FileRead fileRead=new FileRead();
        try {
            String content=fileRead.readFile(args);

            JSONParser p=new JSONParser();
            j= p.parse(content);


            FileData fileData=FileData.getInstance();
            fileData.setFileData(content);
            fileData.setFileName(fileRead.getFileName());
            fileData.setFilePath(args);
            fileData.setFileOpen(true);
            System.out.println("Successfully opened "+ fileData.getFileName());

            return j;
        }catch (FileException | ValidateException fe)
        {
            System.out.println(fe.getMessage());
        }
        return j;
    }

    @Override
    public String getDescription() {
        return "opens <file>";
    }
}

