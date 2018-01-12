package bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol;
import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.USTBPCommands.USTBPCommand;
import bgu.spl181.net.srv.bidi.ConnectionsImpl;


import java.util.concurrent.ConcurrentHashMap;

public class BidiMessagingProtocolImpl implements BidiMessagingProtocol<String> {
    private boolean shouldTerminate=false;
    private int connectionID;
    private ConnectionsImpl connections;
    private ConcurrentHashMap<String, String> AllOnlineUsers;
    private String UserName;

    public BidiMessagingProtocolImpl(ConcurrentHashMap<String,String> onlineUsers){
        this.AllOnlineUsers=onlineUsers;
    }

    public void start(int connectionId, Connections<String> connections){
        connectionID=connectionId;
        this.connections=(ConnectionsImpl)connections;

    }

    public void process(String message){
        Result result=proccessCommand(message);
        connections.send(connectionID,"answer");
}

    private Result proccessCommand(String message){
        String commandName=null;
        USTBPCommand command=null;
        Result result =null;

        switch(commandName) {
            case "REGISTER": {}

            case "LOGIN": {}

            case "SIGNOUT": {}

            case "REQUEST": {}


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


