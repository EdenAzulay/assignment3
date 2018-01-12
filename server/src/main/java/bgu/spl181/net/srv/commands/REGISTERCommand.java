package bgu.spl181.net.srv.commands;

import bgu.spl181.net.impl.dbClasses.BBUser;
import bgu.spl181.net.impl.dbClasses.DbHandler;
import bgu.spl181.net.srv.Result;

public class REGISTERCommand<String> extends BaseCommand {
private BBUser user;

    public REGISTERCommand(DbHandler dbHandler, BBUser user) {
        super(dbHandler);
        this.user = user;
    }


    @Override
    Result execute() {

        return null;
    }
}

