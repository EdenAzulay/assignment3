package bgu.spl181.net.srv;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;

public class MovieRentProtocol<String> implements BidiMessagingProtocol<String> {
    private boolean shouldTerminate=false;
    public void start(int connectionId, Connections<String> connections){}

    public void process(String message){}

    /**
     * @return true if the connection should be terminated
     */
    public boolean shouldTerminate(){
        return shouldTerminate;
    }
}

