package bgu.spl181.net.srv.commands;

import bgu.spl181.net.impl.dbClasses.MoviesJsonHandler;
import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;
import bgu.spl181.net.srv.Result;

public abstract class BaseCommand {
    protected MoviesJsonHandler moviesJsonHandler;
    protected UsersJsonHandler usersJsonHandler;
    protected String[] messageArr;
    String result;

    public BaseCommand(UsersJsonHandler usersJsonHandler, String[] messageArr){
        this.usersJsonHandler = usersJsonHandler;
        this.messageArr = messageArr;
    }



    abstract String execute();
}
