package main;

import main.commands.CommandHandler;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CommandHandler commandHandler = new CommandHandler();
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        commandHandler.executeCommand(input);
        System.out.println(input);
    }
}
