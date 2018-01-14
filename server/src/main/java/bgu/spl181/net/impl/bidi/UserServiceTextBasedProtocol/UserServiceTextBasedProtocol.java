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
    private ConnectionsImpl connections;
    //private MovieRentProtocol protocol;
    private boolean isLogged;
    private String logginUserName;
    private UsersJsonHandler usersJsonHandler;
    private ConcurrentHashMap<String,String> loggedUsers;
    private IService service;

    public UserServiceTextBasedProtocol(UsersJsonHandler usersJsonHandler, ConcurrentHashMap<String,String> loggedUsers, IService service){
        this.loggedUsers=loggedUsers;
        this.usersJsonHandler = usersJsonHandler;
        isLogged=false;
        logginUserName="";
        this.service=service;
    }

    public void start(int connectionId, Connections<String> connections){
        clientID=connectionId;
        this.connections=(ConnectionsImpl)connections;

    }

    public void process(String message){

        String result = handleMessage(message);
        connections.send(clientID,"answer");

}
    private String handleMessage(String message) {
        String commandName=null;
        String result=null;
        String[] messageArr = message.split("\\s+");
        BaseCommand command=null;

        if(message.length()>0){
            commandName = messageArr[0];
        }

        switch (commandName) {
            //SIGNOUT
            case "SIGNOUT": {
                if (messageArr.length == 1 && isLogged){
                    isLogged = false;
                    loggedUsers.remove(logginUserName);
                    logginUserName = "";
                    connections.send(this.clientID, "ACK signout succeeded");
                }
                else{
                    connections.send(this.clientID, "ERROR signout failed");
                }
                //TODO-CHECK WHAT SHOULD BE RESULT
            }


            case "REGISTER": {
                result = "ERROR registration failed";
                if (!isLogged) {
                     command= new REGISTERCommand(messageArr, usersJsonHandler);
                    result = command.execute();
                }

                connections.send(this.clientID,result);
            }


            case "LOGIN": {
                result = "false";
                if (!isLogged){
                    command = new LOGINCommand(messageArr,usersJsonHandler,loggedUsers);
                    result = command.execute();
                }
                if (!result.equals("false")){
                    logginUserName = result;
                    isLogged = true;
                    result = "ACK login succeeded";
                }
                else
                    result = "ERROR login failed";

                connections.send(this.clientID,result);
            }

            case "REQUEST": {
                if(messageArr.length>1){
                    String type=messageArr[1];
                    command=new REQUESTCommand(type,logginUserName,messageArr,service, usersJsonHandler);
                    result=command.execute();
                }

                //                    if (result.hasBroadcase()) {
                //                        broadcast(result.getBroadcast());
                //                    }
                //                }
                //
                //                break;
                //            }

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
}


