package bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.Commands;


import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.ResultObj;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.ResultObjType;
import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LOGINCommand extends BaseCommand {
    private String ErrorOutput = "ERROR login failed";
    private String SuccessOutput="ACK login succeeded";
    private Map<String,String> loggedUsers;
    private boolean isLogged;
    private int clientID;

    public LOGINCommand(String[] messageArr, UsersJsonHandler usersJsonHandler, Map<String,String> loggedUsers, boolean isLogged, int clientID) {
        super(usersJsonHandler,messageArr);

        this.loggedUsers=loggedUsers;
        this.isLogged=isLogged;
        this.clientID=clientID;
    }

    @Override
    public ResultObj execute() {
        if(isLogged)
            return new ResultObj(ResultObjType.ERROR,ErrorOutput);

        if(this.messageArr.length!=3)
            return new ResultObj(ResultObjType.ERROR,ErrorOutput);

        if(!usersJsonHandler.validateCredentials(messageArr[1],messageArr[2]))
            return new ResultObj(ResultObjType.ERROR,ErrorOutput);

        for (String s : loggedUsers.keySet()) {
            if(s.equals(messageArr[1]))
                return new ResultObj(ResultObjType.ERROR,ErrorOutput);
        }

        if(loggedUsers.putIfAbsent(messageArr[1],String.valueOf(clientID))!=null)
            return new ResultObj(ResultObjType.ERROR,ErrorOutput);


        return new ResultObj(ResultObjType.ACK,SuccessOutput);

    }
}
