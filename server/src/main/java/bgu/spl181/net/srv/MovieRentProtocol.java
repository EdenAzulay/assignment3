package bgu.spl181.net.srv;
import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.rci.*;
import bgu.spl181.net.srv.*;

import java.io.Serializable;
import java.util.Map;

public class MovieRentProtocol implements BidiMessagingProtocol {
    private boolean shouldTerminate=false;
    private int owner;

    public void start(int connectionId, Connections connections){
        owner=connectionId;
        
    }

    public void process(Object message){
       String msg=(String)message;
       String StrCommand=msg.substring(0,msg.indexOf('<'));
       String args=msg.substring(msg.indexOf('<'),msg.length());
       BaseCommand<String> cm = null;
        switch (StrCommand){
            case "ACK": cm= new ACKCommand();
            case "ERROR":cm= new ERRORCommand();
            case "BROADCAST":cm=new BROADCASTCommand();
            case "REGISTER":cm=new REGISTERCommand();
            case "LOGIN": cm=new LOGINCommand();
            case "SIGNOUT":cm=new SIGNOUTCommand();
            case "REQUEST":cm=new REQUESTCommand();
        }
        if(cm!=null)
            cm.execute(args);
    }

    /**
     * @return true if the connection should be terminated
     */
    public boolean shouldTerminate(){
        return shouldTerminate;
    }
}

