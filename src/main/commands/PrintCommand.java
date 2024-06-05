package main.commands;

import main.contracts.Command;
import main.contracts.JSON;

public class PrintCommand implements Command {

    @Override
    public JSON execute(String args, JSON j) {
        System.out.println(j.stringify());
        return j;
    }


    @Override
    public String getDescription() {
        return "Print the json.";
    }
}
