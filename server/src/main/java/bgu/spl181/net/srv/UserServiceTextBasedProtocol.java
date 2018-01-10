package bgu.spl181.net.srv;
import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.BBreactor.ConnectionsImpl;
import bgu.spl181.net.srv.commands.LOGINCommand;
import bgu.spl181.net.srv.commands.REGISTERCommand;
import bgu.spl181.net.srv.commands.SIGNOUTCommand;

import java.util.concurrent.ConcurrentHashMap;

public class UserServiceTextBasedProtocol implements BidiMessagingProtocol<String> {
    private boolean shouldTerminate=false;
    private int clientID;
    private ConnectionsImpl connections;
    private MovieRentProtocol protocol;

    private ConcurrentHashMap<Integer, String> loggedInUsers;
    private String commandName;

    public UserServiceTextBasedProtocol(){

    }

    public void start(int connectionId, Connections<String> connections){
        clientID=connectionId;
        this.connections=(ConnectionsImpl)connections;

    }

    public void process(String message){

        handleMessage(message);





        connections.send(clientID,"answer");
}
    private Result handleMessage(String message) {

        //calculating how many spaces in message
        int numOfSpaces;
        String tmp = message;
        tmp.replace(" ", "");
        numOfSpaces = message.length() - tmp.length();

        //get the commandName
        int spaceIndex = message.indexOf(" ");
        if (numOfSpaces == 0) {
            commandName = message;
        } else if (numOfSpaces > 0) {
            commandName = message.substring(0, spaceIndex);
        }

        boolean executed = false;
        String userName,password;
        String dataBlock ="";

        switch (commandName) {
            //SIGNOUT
            case "SIGNOUT": {
                SIGNOUTCommand command = new SIGNOUTCommand<>();
                executed = true;
                command.execute();
            }
            //REGISTER <username> <password> [Data block,…]
            case "REGISTER": {
                if (numOfSpaces>=2){                //making sure the input is valid

                    message =  message.substring(spaceIndex + 1);
                    spaceIndex = message.indexOf(" ");
                    userName = message.substring(0,spaceIndex);

                    message =  message.substring(spaceIndex + 1);
                    spaceIndex = message.indexOf(" ");
                    password = message.substring(0,spaceIndex);

                    if (numOfSpaces>2){ //todo: check how to handle the dataBlock
                        message =  message.substring(spaceIndex + 1);
                        dataBlock = message;
                    }
                    REGISTERCommand command = new REGISTERCommand(userName,password,dataBlock);
                    executed = true;
                    command.execute();
                }
            }
            //LOGIN <username> <password>
            case "LOGIN": {
                if (numOfSpaces==2) {                //making sure the input is valid
                    message = message.substring(spaceIndex + 1);
                    spaceIndex = message.indexOf(" ");
                    userName = message.substring(0, spaceIndex);

                    message = message.substring(spaceIndex + 1);
                    spaceIndex = message.indexOf(" ");
                    password = message.substring(0, spaceIndex);

                    LOGINCommand command = new LOGINCommand(userName,password);
                    executed = true;
                    command.execute();
                }

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


