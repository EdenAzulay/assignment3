package bgu.spl181.net.srv;
import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;

public class ServerProtocol implements BidiMessagingProtocol<String> {
    private boolean shouldTerminate=false;
    private int clientID;
    private _Connections connections;
    private MovieRentProtocol protocol;

    public void start(int connectionId, Connections<String> connections){
        clientID=connectionId;
        this.connections=(_Connections)connections;

    }

    public void process(String message){

}

    /**
     * @return true if the connection should be terminated
     */
    public boolean shouldTerminate(){
        return shouldTerminate;
    }
}

