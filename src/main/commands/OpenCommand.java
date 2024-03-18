package main.commands;

import main.File.FileRead;
import main.contracts.Command;


public class OpenCommand implements Command {
    @Override
    public void execute(String[] args) {
        String filePath=args[0];
        FileRead fileRead=new FileRead();
        String content=fileRead.readFile(filePath);
        System.out.println(fileRead.getFileName());
    }
}

