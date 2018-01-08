package bgu.spl181.net.impl.rci.Commands.SubCommands;

import bgu.spl181.net.impl.rci.Commands.ACK;

import java.io.Serializable;

public class RegistrationACK extends ACK {

    public RegistrationACK(String userName, String password, String[] dataBlock){
        this.description = "ACK registration succeeded";
    }


    @Override
    public Serializable execute(Object arg) {

        return null;
    }
}
