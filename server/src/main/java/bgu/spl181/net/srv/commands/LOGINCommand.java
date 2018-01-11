package bgu.spl181.net.srv.commands;

import bgu.spl181.net.impl.dbClasses.DbHandler;
import bgu.spl181.net.srv.Result;


public class LOGINCommand<String> extends BaseCommand {
    public LOGINCommand(DbHandler dbHandler) {
        super(dbHandler);
    }

    @Override
    Result execute() {
        return null;
    }
}
