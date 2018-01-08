package bgu.spl181.net.srv;

import bgu.spl181.net.api.bidi.MessagingProtocol;
import bgu.spl181.net.srv.Commands.*;

public class MovieRentProtocol implements MessagingProtocol<Message> {
    private boolean shouldTerminate=false;


    public void process(Message message){
        message.
    }

    public boolean shouldTerminate(){ return shouldTerminate;}

}
