package bgu.spl181.net.srv;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;

import java.sql.Connection;
import java.util.Map;
import java.*;

public class MovieRentProtocol<String> implements BidiMessagingProtocol<String> {
    private boolean shouldTerminate=false;
    private Map coneectionsMap;

    public MovieRentProtocol(Map coneectionsMap) {

    }

    public void start(int connectionId, Connections<String> connections){}

    public void process(String message){
        String s = (String) "FF";
    }

    /**
     * @return true if the connection should be terminated
     */
    public boolean shouldTerminate(){
        return shouldTerminate;
    }
}

