package bgu.spl181.net.srv;
import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.BBreactor.ConnectionsImpl;

import java.util.concurrent.ConcurrentHashMap;

public class UserServiceTextBasedProtocol implements BidiMessagingProtocol<String> {
    private boolean shouldTerminate=false;
    private int clientID;
    private ConnectionsImpl connections;
    private MovieRentProtocol protocol;

    private ConcurrentHashMap<Integer, String> loggedInUsers;
    private

    public UserServiceTextBasedProtocol(){

    }

    public void start(int connectionId, Connections<String> connections){
        clientID=connectionId;
        this.connections=(ConnectionsImpl)connections;

    }

    public void process(String message){




        connections.send(clientID,"answer");
}

    /**
     * @return true if the connection should be terminated
     */
    public boolean shouldTerminate(){
        return shouldTerminate;
    }
}


