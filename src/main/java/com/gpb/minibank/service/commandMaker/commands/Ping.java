package com.gpb.minibank.service.commandMaker.commands;


public class Ping implements Command {

    @Override
    public String exec() {
        return "pong";
    }
}
