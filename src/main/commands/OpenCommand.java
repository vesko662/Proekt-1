package main.commands;

import main.exeptions.FileException;
import main.file.FileRead;
import main.contracts.Command;
import main.singletons.FileData;

public class OpenCommand implements Command {
    @Override
    public void execute(String args) {
        FileRead fileRead=new FileRead();

        try {
            String content=fileRead.readFile(args);
            FileData fileData=FileData.getInstance();
            fileData.setFileData(content);
            fileData.setFileName(fileRead.getFileName());
            fileData.setFilePath(args);
            fileData.setFileOpen(true);
        }catch (FileException fe)
        {
            System.out.println(fe.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "opens <file>";
    }
}

