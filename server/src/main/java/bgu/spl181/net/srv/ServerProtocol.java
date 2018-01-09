package bgu.spl181.net.srv;
import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;

public class ServerProtocol implements BidiMessagingProtocol<Message> {
    private boolean shouldTerminate=false;
    private int owner;
    private MovieRentProtocol protocol;

    public void start(int connectionId, Connections<Message> connections){
        owner=connectionId;

    }

    public void process(Message message){
        String type=message.getType();
        if(type.equals("General"))
            message.execute();
        else if(type.equals("Specific"))
            protocol.process(message);
    }

    /**
     * @return true if the connection should be terminated
     */
    public boolean shouldTerminate(){
        return shouldTerminate;
    }
}

