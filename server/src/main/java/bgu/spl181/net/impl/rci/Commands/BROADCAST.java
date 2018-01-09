package bgu.spl181.net.impl.rci.Commands;

import bgu.spl181.net.impl.rci.Command;

import java.io.Serializable;

public abstract class BROADCAST implements Command {
    protected String description;


    @Override
    public abstract Serializable execute(Object arg);
}
