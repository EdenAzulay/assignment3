package bgu.spl181.net.srv;
import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.rci.*;
import bgu.spl181.net.srv.*;

public class MovieRentProtocol implements BidiMessagingProtocol {
    private boolean shouldTerminate=false;
    public void start(int connectionId, Connections<String> connections){
        boolean bol;
    }

    public void process(String msg){
       String StrCommand=msg.substring(0,msg.indexOf('<'));
       String args=msg.substring(msg.indexOf('<'),msg.length());
       Command<String> cm;
        switch (StrCommand){
            case "ACK": cm= new ACKCommand(args);
            case "ERROR":cm= new ERRORCommand(args);
            case "BROADCAST":cm=new BROADCASTCommand(args);
            case "REGISTER":cm=new REGISTERCommand(args);
            case "LOGIN": cm=new LOGINCommand(args);
            case "SIGNOUT":cm=new SIGNOUTCommand(args);
            case "REQUEST":cm=new REQUESTCommand(args);
        }
        return cm.execute();
    }

    /**
     * @return true if the connection should be terminated
     */
    public boolean shouldTerminate(){
        return shouldTerminate;
    }
}

