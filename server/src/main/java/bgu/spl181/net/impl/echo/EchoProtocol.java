package bgu.spl181.net.impl.echo;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.rci.*;
import bgu.spl181.net.srv.*;

public class EchoProtocol implements BidiMessagingProtocol<String> {
    private int owner; //TODO-should be initialized with 0?
    private Connections<String> connections;
    private boolean shouldTerminate = false;

    @Override
    public void process(String msg) { }

/*
    private String createEcho(String message) {
        String echoPart = message.substring(Math.max(message.length() - 2, 0), message.length());
        return message + " .. " + echoPart + " .. " + echoPart + " ..";
    }
*/

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    @Override
    public void start(int connectionId, Connections<String> connections) {
        this.connections=connections;//TODO- check this line
        owner=connectionId;

    }
}
