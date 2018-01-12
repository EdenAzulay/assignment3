package bgu.spl181.net.srv.commands;

import bgu.spl181.net.impl.dbClasses.DbHandler;
import bgu.spl181.net.srv.Result;

import java.io.Serializable;

public class REQUESTCommand<String> extends BaseCommand{

    public REQUESTCommand(DbHandler dbHandler) {
        super(dbHandler);
    }

    @Override
    Result execute() {
        return null;
    }
}
