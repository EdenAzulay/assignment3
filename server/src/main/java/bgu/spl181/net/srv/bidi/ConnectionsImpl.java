package bgu.spl181.net.srv.bidi;

import bgu.spl181.net.srv.bidi.ConnectionHandler;
import bgu.spl181.net.api.bidi.Connections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionsImpl<T> implements Connections<T> {
    private ConcurrentHashMap<Integer,ConnectionHandler<T>> connectedClientsCHMap;
    private AtomicInteger connectionId;

    public ConnectionsImpl(){
        this.connectedClientsCHMap = new ConcurrentHashMap<>();
        this.connectionId = new AtomicInteger(0);
    }

    //send a message to a client represented by the given connectionId
    @Override
    public boolean send(int connectionId, T msg) {
        boolean output = false;
        ConnectionHandler<T> ch = connectedClientsCHMap.get(connectionId);

        if (ch != null){
            ch.send(msg);
            output = true;
        }
        return output;
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
        for (Map.Entry<Integer, ConnectionHandler<T>> entry : connectedClientsCHMap.entrySet()) {
            ConnectionHandler<T> ch = entry.getValue();
            if(ch!=null)
                ch.send(msg);
        }
    }


    //removes active client connId from map
    @Override
    public void disconnect(int connectionId) {
        this.connectedClientsCHMap.remove(connectionId);
    }
    //TODO- check if there is no need for closing the connection, just removing from list.

    public void connectClient(ConnectionHandler<T> connectionHandler) {
        this.connectedClientsCHMap.put(generateConnectionId(), connectionHandler);
        //TODO- check if there is no need for a specific ID
    }

    public int generateConnectionId(){
        return this.connectionId.getAndIncrement();
    }

    public ConnectionHandler<T> getConnectionHandler(int connectionId){
        return this.connectedClientsCHMap.get(connectionId);
    }




}