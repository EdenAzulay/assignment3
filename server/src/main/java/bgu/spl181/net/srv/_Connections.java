package bgu.spl181.net.srv;

import bgu.spl181.net.api.bidi.ConnectionHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class _Connections<T> implements bgu.spl181.net.api.bidi.Connections<T> {
    private ConcurrentHashMap<Integer,ConnectionHandler<T>> loggedInCHMap;
    private AtomicInteger uniqueCnnectionId;

    public _Connections(){
        this.loggedInCHMap = new ConcurrentHashMap<>();
        this.uniqueCnnectionId = new AtomicInteger(0);
    }


    @Override
    public boolean send(int connectionId, T msg) {
        return false;
    }

    /*
    sends a message T to all active clients. This
    includes clients that has not yet completed log-in by the User service text
    based protocol. Remember, Connections<T> belongs to the server pattern
    implemenration, not the protocol!.
     */

    //Send a message to all the logged in clients.
    @Override
    public void broadcast(T msg) {
        for (Map.Entry<Integer, ConnectionHandler<T>> entry : loggedInCHMap.entrySet()) {
            ConnectionHandler<T> ch = entry.getValue();
            ch.send(msg);
        }
    }


    //removes active client connId from map
    @Override
    public void disconnect(int connectionId) {


        }



}
