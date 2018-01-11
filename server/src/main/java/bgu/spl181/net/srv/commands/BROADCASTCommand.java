package bgu.spl181.net.srv.commands;

import bgu.spl181.net.impl.dbClasses.DbHandler;
import bgu.spl181.net.srv.Result;

import java.io.Serializable;

public class BROADCASTCommand<String> extends BaseCommand {
    public BROADCASTCommand(DbHandler dbHandler) {
        super(dbHandler);
    }

    @Override
    Result execute() {
        return null;
    }
}
