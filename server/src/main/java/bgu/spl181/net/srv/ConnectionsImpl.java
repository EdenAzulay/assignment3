package bgu.spl181.net.srv;

import bgu.spl181.net.api.bidi.Connections;

public class ConnectionsImpl implements Connections<Message> {

    public boolean send(int connectionId, Message msg){}

    public void broadcast(Message msg){}

    public void disconnect(int connectionId){}
}
