package com.gpb.minibank.service.commandMaker.commands;

import org.springframework.stereotype.Component;

@Component
public class Ping {

    public String exec() {
        return "pong";
    }
}
