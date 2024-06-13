package main.commands;

import main.contracts.Command;
import main.contracts.JSON;
import main.singletons.FileData;

public class CloseCommand implements Command {
    @Override
    public JSON execute(String args, JSON j) {
        FileData fileData=FileData.getInstance();
        fileData.setFileData("");
        String name=fileData.getFileName();
        fileData.setFileName("");
        fileData.setFilePath("");
        fileData.setFileOpen(false);
        System.out.println("Successfully closed "+name);

        return null;
    }

    @Override
    public String getDescription() {
        return "closes currently opened file";
    }
}
