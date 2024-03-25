package main;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CommandHandler commandHandler = new CommandHandler();
        Scanner scanner = new Scanner(System.in);

        while (true)
        {
            String input = scanner.nextLine();
            commandHandler.executeCommand(input);
        }


    }
}
