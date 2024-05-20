package main.commands;

import main.exceptions.FileException;
import main.file.FileWrite;
import main.contracts.Command;
import main.singletons.FileData;

public class SaveAsCommand implements Command {
    @Override
    public void execute(String args) {
        FileWrite fw=new FileWrite();
        FileData fd=FileData.getInstance();
        try {
            fw.writeFile(args,fd.getFileData());
        } catch (FileException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Successfully saved another "+ fd.getFileName());
    }

    @Override
    public String getDescription() {
        return "saves the currently open file in <file>";
    }
}
