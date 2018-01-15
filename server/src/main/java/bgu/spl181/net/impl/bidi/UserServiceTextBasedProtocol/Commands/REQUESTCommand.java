package bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.Commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.bidi.IService;
import bgu.spl181.net.impl.dbClasses.MoviesJsonHandler;
import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;
import bgu.spl181.net.srv.bidi.ConnectionsImpl;

public class REQUESTCommand extends BaseCommand {
    private String type, userName;
    private boolean isLogged;
    private IService service;
    private UsersJsonHandler usersJsonHandler;
    private ConnectionsImpl connections;

    public REQUESTCommand(String username, String[] messageArr, IService service, UsersJsonHandler usersJsonHandler, ConnectionsImpl connections) {
        super(usersJsonHandler, messageArr);
        this.isLogged = !username.equals("");
        this.userName = username;
        this.service = service;
        this.usersJsonHandler = usersJsonHandler;
        this.messageArr = messageArr;
        this.connections=connections;
    }

    @Override
    public String execute() {

        if (isLogged)
            return service.handle(userName, messageArr);

        else
            return "ERROR request " + type + " failed";
    }

}