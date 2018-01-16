package bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol;
import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.bidi.IService;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.Commands.BaseCommand;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.Commands.REQUESTCommand;
import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;
import bgu.spl181.net.srv.bidi.ConnectionsImpl;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.Commands.REGISTERCommand;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.Commands.LOGINCommand;


import java.util.concurrent.ConcurrentHashMap;

public class UserServiceTextBasedProtocol implements BidiMessagingProtocol<String> {
    private boolean shouldTerminate=false;
    private int clientID;
    private Connections<String> connections;
    //private MovieRentProtocol protocol;
    private String logginUserName;
    private UsersJsonHandler usersJsonHandler;
    private ConcurrentHashMap<String,String> loggedUsers;
    private IService service;

    public UserServiceTextBasedProtocol(UsersJsonHandler usersJsonHandler, ConcurrentHashMap<String,String> loggedUsers, IService service){
        this.loggedUsers=loggedUsers;
        this.usersJsonHandler = usersJsonHandler;
        logginUserName=null;
        this.service=service;
    }

    public void start(int connectionId, Connections<String> connections){
        clientID=connectionId;
        this.connections=connections;

    }

    public void process(String message) {

        ResultObj result = handleMessage(message);

        connections.send(clientID, result.getResponse());

        if (result.hasBroadcase())
            broadcast(result);

    }

    private ResultObj handleMessage(String message) {
        String commandName=null;
        ResultObj result=null;
        String[] messageArr = message.split("\\s+");
        BaseCommand command=null;

        if(message.length()>0){
            commandName = messageArr[0];
        }

        switch (commandName) {

            case "SIGNOUT": {
                if (messageArr.length == 1 && isLogged()) {
                    loggedUsers.remove(logginUserName);
                    logginUserName = null;
                    return new ResultObj(ResultObjType.ACK,"ACK signout succeeded");
                }

                return new ResultObj(ResultObjType.ERROR,"ERROR signout failed");
            }

            case "REGISTER": {

                command= new REGISTERCommand(messageArr, usersJsonHandler,service,isLogged());
                result = command.execute();

                break;
            }

            case "LOGIN": {
                command = new LOGINCommand(messageArr, usersJsonHandler, loggedUsers, isLogged(), clientID);
                result = command.execute();

                if (result.getType() == ResultObjType.ACK) logginUserName = messageArr[1];

                break;
            }

            case "REQUEST": {
                if (messageArr.length > 1) {
                    command = new REQUESTCommand(logginUserName, messageArr, service, usersJsonHandler, isLogged());
                    result = command.execute();

                    break;
                }
            }

        }
        return result;
    }

    /**
     * @return true if the connection should be terminated
     */
    public boolean shouldTerminate(){
        return shouldTerminate;
    }

    public boolean isLogged(){ return logginUserName != null;}

    public void broadcast(ResultObj result) {
        String broadcastMessage = result.getBroadcast();
        for (String username : this.loggedUsers.keySet())
            this.connections.send(Integer.parseInt(this.loggedUsers.get(username)), broadcastMessage);
    }

}


