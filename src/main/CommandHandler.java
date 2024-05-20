package main;

import main.commands.*;
import main.contracts.Command;
import main.exceptions.CommandException;
import main.singletons.FileData;
import java.util.*;


public class CommandHandler {
    private  Map<String, Command> commands = new HashMap<>();

    public CommandHandler() {
        commands.put("open", new OpenCommand());
        commands.put("exit",new ExitCommand());
        commands.put("help", new HelpCommand(commands));
    }

    public void executeCommand(String input) {
        String[] sInput = input.split(" ",2);
        String commandKeyword = sInput[0];

        Command command = commands.get(commandKeyword);
        if (command != null) {
            try {
                command.execute(sInput.length ==1 ? "" : sInput[1]);
                checkFileState();
            } catch (CommandException ce) {
                System.out.println(ce.getMessage());
            }

        } else {
            System.out.println("Invalid command.");
        }

    }

    private void checkFileState()
    {
        if(FileData.getInstance().isFileOpen())
        {
            if (commands.size()>3) {
                return;
            }
            commands.put("close", new CloseCommand());
            commands.put("save", new SaveCommand());
            commands.put("saveas", new SaveAsCommand());
            commands.put("print", new PrintCommand());
            commands.put("search",new SearchCommand());
            commands.put("delete",new DeleteCommand());
            commands.put("move",new MoveCommand());
            commands.put("set",new SetCommand());
            commands.put("create",new CreateCommand());
            commands.put("exit",new ExitCommand());
        }
        else
        {
            commands = new HashMap<>();
            commands.put("open", new OpenCommand());
            commands.put("exit",new ExitCommand());
            commands.put("help", new HelpCommand(commands));
        }
    }
}
//open D:\Programing\University\OOP2\proect\file.json