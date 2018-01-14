package bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol;
import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;
import bgu.spl181.net.srv.bidi.ConnectionsImpl;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.commands.REQUESTCommand;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.commands.REGISTERCommand;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.commands.LOGINCommand;


import java.util.concurrent.ConcurrentHashMap;

public class UserServiceTextBasedProtocol implements BidiMessagingProtocol<String> {
    private boolean shouldTerminate=false;
    private int clientID;
    private ConnectionsImpl connections;
    //private MovieRentProtocol protocol;
    private boolean isLogged;
    private String logginUser;
    private String commandName;
    UsersJsonHandler usersJsonHandler;
    ConcurrentHashMap<String,String> loggedUsers;

    public UserServiceTextBasedProtocol(UsersJsonHandler usersJsonHandler, ConcurrentHashMap<String,String> loggedUsers){
        this.loggedUsers=loggedUsers;
        this.usersJsonHandler = usersJsonHandler;
        isLogged=false;
        logginUser="";
    }

    public void start(int connectionId, Connections<String> connections){
        clientID=connectionId;
        this.connections=(ConnectionsImpl)connections;

    }

    public void process(String message){

        Result result = handleMessage(message);



        //todo::send result

        connections.send(clientID,"answer");
}
    private Result handleMessage(String message) {

        String[] messageArr = message.split("\\s+");

        if(message.length()>0){
            commandName = messageArr[0];
        }

        switch (commandName) {
            //SIGNOUT
            case "SIGNOUT": {
                if (messageArr.length == 1 && isLogged){
                    isLogged = false;
                    loggedUsers.remove(logginUser);
                    logginUser = "";
                    connections.send(this.clientID, "ACK signout succeeded");
                }
                else{
                    connections.send(this.clientID, "ERROR signout failed");
                }
            }
            //REGISTER <username> <password> [Data block,…]
            case "REGISTER": {
                String result = "ERROR registration failed";
                if (!isLogged) {
                    REGISTERCommand command= new REGISTERCommand(messageArr, usersJsonHandler);
                    result = command.execute();
                }

                connections.send(this.clientID,result);
            }
            //LOGIN <username> <password>
            case "LOGIN": {
                String result = "false";
                if (!isLogged){
                    LOGINCommand command = new LOGINCommand(messageArr,usersJsonHandler,loggedUsers);
                    result = command.execute();
                }
                if (!result.equals("false")){
                    logginUser = result;
                    isLogged = true;
                    result = "ACK login succeeded";
                }
                else
                    result = "ERROR login failed";

                connections.send(this.clientID,result);
            }

            //REQUEST <name> [parameters,…]
            case "REQUEST": {
                //todo

            }

            //input is invalid
            if (!executed){
                //todo: check what is the requirement for invalid input
            }
        }
    }



    /**
     * @return true if the connection should be terminated
     */
    public boolean shouldTerminate(){
        return shouldTerminate;
    }
}


