package main.commands;

import main.contracts.Command;
import main.singletons.FileData;

public class CloseCommand implements Command {
    @Override
    public void execute(String args) {
        FileData fileData=FileData.getInstance();
        fileData.setFileData("");
        fileData.setFileName("");
        fileData.setFilePath("");
        fileData.setFileOpen(false);
    }

    @Override
    public String getDescription() {
        return "closes currently opened file";
    }
}
