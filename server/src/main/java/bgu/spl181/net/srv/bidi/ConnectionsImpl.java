package bgu.spl181.net.srv.bidi;

import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.ResultObj;
import bgu.spl181.net.srv.bidi.ConnectionHandler;
import bgu.spl181.net.api.bidi.Connections;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
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
        ConnectionHandler<T> connectionHandler = connectedClientsCHMap.get(connectionId);
        if (connectionHandler != null){
            connectionHandler.send(msg);
            output = true;
        }
        return output;
    }

/**
    sends a message T to all active clients. This
    includes clients that has not yet completed log-in by the User service text
    based protocol. Remember, Connections<T> belongs to the server pattern
    implemenration, not the protocol!.
*/

    //Send a message to all the logged in clients.
    public void broadcast(T msg) {
        //getting all connectionHandlers
        Set<Map.Entry<Integer, ConnectionHandler<T>>> entries = connectedClientsCHMap.entrySet();
        for (Map.Entry<Integer, ConnectionHandler<T>> entry : entries) {
            ConnectionHandler<T> connectionHandler = entry.getValue();
            //calling send with msg for each one
            if (connectionHandler != null) {
                connectionHandler.send(msg);
            }
        }
    }


    //removes active client connId from map
    @Override
    public void disconnect(int connectionId) {
        ConnectionHandler<T> connectionHandler = connectedClientsCHMap.get(connectionId);
        if (connectionHandler != null) {
            try {
                connectionHandler.close();
                this.connectedClientsCHMap.remove(connectionId);
            }
            catch (IOException e) {//empty
            }
        }
    }

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