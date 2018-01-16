package bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.Commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.bidi.IService;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.ResultObj;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.ResultObjType;
import bgu.spl181.net.impl.dbClasses.MoviesJsonHandler;
import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;
import bgu.spl181.net.srv.bidi.ConnectionsImpl;

public class REQUESTCommand extends BaseCommand {
    private String  userName;
    private boolean isLogged;
    private IService service;
    private UsersJsonHandler usersJsonHandler;
    private ConnectionsImpl connections;

    public REQUESTCommand(String username, String[] messageArr, IService service, UsersJsonHandler usersJsonHandler,boolean isLogged) {
        super(usersJsonHandler, messageArr);
        this.isLogged = isLogged;
        this.userName = username;
        this.service = service;
        this.usersJsonHandler = usersJsonHandler;
    }

    @Override
    public ResultObj execute() {

        if (isLogged)
            return service.handle(userName, messageArr);

        else
            return new ResultObj(ResultObjType.ERROR, ("ERROR request " + messageArr[1] + " failed"));
    }

}