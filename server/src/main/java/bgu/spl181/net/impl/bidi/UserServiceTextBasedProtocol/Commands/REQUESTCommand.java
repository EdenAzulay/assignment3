package bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.Commands;

import bgu.spl181.net.impl.bidi.IService;
import bgu.spl181.net.impl.dbClasses.MoviesJsonHandler;
import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;

public class REQUESTCommand extends BaseCommand{
    private String type, userName;
    private boolean isLogged;
    private IService service;
    private UsersJsonHandler usersJsonHandler;

    public REQUESTCommand(String type, String username, String[] messageArr , IService service, UsersJsonHandler usersJsonHandler) {
         super(usersJsonHandler, messageArr);
         this.isLogged=!username.equals("");
         this.type=type;
         this.userName=username;
         this.service=service;
         this.usersJsonHandler=usersJsonHandler;
    }

    @Override
    public java.lang.String execute() {
        return null;
    }
    //TODO- FINISH EXECUTE.
}


//    @Override
//    public Result execute() {
//        if(!isLoggedIn){
//            return new Result(ResultType.ERROR, String.format(ERROR_MESSAGE, name));
//        }
//
//        return service.handle(name, username, params);
//    }
//}