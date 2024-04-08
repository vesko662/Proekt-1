package main;

import main.commands.*;
import main.contracts.Command;
import main.singletons.FileData;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private  Map<String, Command> commands;

    public CommandHandler() {
        commands = new HashMap<>();
        commands.put("open", new OpenCommand());
    }

    public void executeCommand(String input) {
        String[] sInput = input.split(" ",2);
        String commandKeyword = sInput[0];

        Command command = commands.get(commandKeyword);
        if (command != null) {
            command.execute(sInput.length==1?"":sInput[1]);
            checkFileState();
        } else {
            System.out.println("Invalid command.");
        }

    }

    private void checkFileState()
    {
        if(FileData.getInstance().isFileOpen())
        {
            if (commands.size()>1) {
                return;
            }
            commands.put("close", new CloseCommand());
            commands.put("save", new SaveCommand());
            commands.put("saveas", new SaveAsCommand());
            commands.put("help", new HelpCommand(commands));
        }
        else
        {
            commands = new HashMap<>();
            commands.put("open", new OpenCommand());
        }
    }
}
//open D:\Programing\University\OOP2\test.txt