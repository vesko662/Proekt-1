package main.commands;

import main.File.FileWrite;
import main.contracts.Command;
import main.singletons.FileData;

public class SaveAsCommand implements Command {
    @Override
    public void execute(String args) {
        FileWrite fw=new FileWrite();
        FileData fd=FileData.getInstance();
        fw.writeFile(args,fd.getFileData());
    }
}
