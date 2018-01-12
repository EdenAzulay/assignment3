package bgu.spl181.net.srv.commands;

import bgu.spl181.net.impl.dbClasses.DbHandler;
import bgu.spl181.net.srv.Result;

import java.io.Serializable;

public abstract class BaseCommand {
    protected DbHandler dbHandler;

    public BaseCommand(DbHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    abstract Result execute();
}
