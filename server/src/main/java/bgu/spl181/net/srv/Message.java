package bgu.spl181.net.srv;

import bgu.spl181.net.srv.Commands.*;

public class Message {
    private String type;
    private String args;
    private BaseCommand command;

    public Message(String str){
        String StrCommand=str.substring(0,str.indexOf('<'));
        args=str.substring(str.indexOf('<'),str.length());
        switch (StrCommand) {
            case "ACK":
                command = new ACKCommand();
                type = "General";
            case "ERROR":
                command= new ERRORCommand();
                type = "General";
            case "BROADCAST":
                command = new BROADCASTCommand();
                type = "General";
            case "REGISTER":
                command= new REGISTERCommand();
                type = "General";
            case "LOGIN":
                command= new LOGINCommand();
                type = "General";
            case "SIGNOUT":
                command= new SIGNOUTCommand();
                type = "General";
            case "REQUEST":
                command= new REQUESTCommand();
                type = "Specific";
        }
    }

    public void execute(){command.execute(args);}

    public String getType() {
        return type;
    }

    public BaseCommand getCommand() {
        return command;
    }
}
