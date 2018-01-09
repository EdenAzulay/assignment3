package bgu.spl181.net.srv;

import bgu.spl181.net.api.bidi.MessagingProtocol;

public class MovieRentProtocol implements MessagingProtocol<String> {
    private boolean shouldTerminate=false;


    public void process(String message){

    }

    public boolean shouldTerminate(){ return shouldTerminate;}

}
