package bgu.spl181.net.srv.commands;


import bgu.spl181.net.impl.dbClasses.DbHandler;
import bgu.spl181.net.srv.Result;

public class ERRORCommand<String> extends BaseCommand{

    public ERRORCommand(DbHandler dbHandler) {
        super(dbHandler);
    }

    @Override
    Result execute() {
        return null;
    }
}
