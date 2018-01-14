package bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.Commands;


import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;

import java.util.concurrent.ConcurrentHashMap;

public class LOGINCommand extends BaseCommand {
    ConcurrentHashMap<String,String> loggedUsers;
    public LOGINCommand(String[] messageArr, UsersJsonHandler usersJsonHandler, ConcurrentHashMap loggedUsers) {
        super(usersJsonHandler,messageArr);
    }

    @Override
    public String execute() {
        boolean loginSucced = false;

        if(messageArr.length == 3){
            loginSucced = usersJsonHandler.validateCredentials(messageArr[1],messageArr[2]);
        }
        for (String s : loggedUsers.keySet()) {
            if(s.equals(messageArr[1]))
                loginSucced=false;
            break;
        }

        if (loginSucced) {
            this.loggedUsers.put(messageArr[1],messageArr[1]);
            return messageArr[1];
        }
        else
            return "false";

    }
}
