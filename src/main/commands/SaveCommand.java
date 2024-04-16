package main.commands;

import main.file.FileWrite;
import main.contracts.Command;
import main.singletons.FileData;

public class SaveCommand implements Command {
    @Override
    public void execute(String args) {
        FileWrite fw=new FileWrite();
        FileData fd=FileData.getInstance();
        fw.writeFile(fd.getFilePath(),fd.getFileData());
    }

    @Override
    public String getDescription() {
        return "saves the currently open file";

    }
}
