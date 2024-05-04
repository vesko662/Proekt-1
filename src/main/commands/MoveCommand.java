package main.commands;

import main.contracts.Command;
import main.singletons.FileData;

public class MoveCommand implements Command {
    @Override
    public void execute(String args) {
        FileData data = FileData.getInstance();
        String json=data.getFileData();
        String[] paths=args.split(" ");


    }



    @Override
    public String getDescription() {
        return null;
    }
}
