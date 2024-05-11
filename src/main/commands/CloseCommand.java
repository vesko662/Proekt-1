package main.commands;

import main.contracts.Command;
import main.singletons.FileData;

public class CloseCommand implements Command {
    @Override
    public void execute(String args) {
        FileData fileData=FileData.getInstance();
        fileData.setFileData("");
        String name=fileData.getFileName();
        fileData.setFileName("");
        fileData.setFilePath("");
        fileData.setFileOpen(false);
        System.out.println("Successfully closed "+name);
    }

    @Override
    public String getDescription() {
        return "closes currently opened file";
    }
}
