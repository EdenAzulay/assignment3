package bgu.spl181.net.srv.Commands;

import bgu.spl181.net.srv.BaseCommand;

import java.io.Serializable;

public class ERRORCommand implements BaseCommand<String> {
    @Override
    public Serializable execute(String arg) {
        return null;
    }
}

