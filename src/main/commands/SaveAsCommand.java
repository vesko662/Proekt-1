package main.commands;

import main.contracts.JSON;
import main.exceptions.FileException;
import main.file.FileWrite;
import main.contracts.Command;
import main.singletons.FileData;

public class SaveAsCommand implements Command {
    @Override
    public JSON execute(String args, JSON j) {
        FileWrite fw=new FileWrite();
        FileData fd=FileData.getInstance();
        try {
            fw.writeFile(args,j.stringify());
        } catch (FileException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Successfully saved another "+ fd.getFileName());
        return j;
    }

    @Override
    public String getDescription() {
        return "saves the currently open file in <file>";
    }
}
