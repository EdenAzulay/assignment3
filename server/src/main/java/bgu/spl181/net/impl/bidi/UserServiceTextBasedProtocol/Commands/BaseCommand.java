package bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.Commands;

import bgu.spl181.net.impl.dbClasses.MoviesJsonHandler;
import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;

public abstract class BaseCommand {
    protected MoviesJsonHandler moviesJsonHandler;
    protected UsersJsonHandler usersJsonHandler;
    protected String[] messageArr;
    String result;

    public BaseCommand(UsersJsonHandler usersJsonHandler, String[] messageArr){
        this.usersJsonHandler = usersJsonHandler;
        this.messageArr = messageArr;
    }



    public abstract String execute();
}
