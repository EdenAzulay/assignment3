package bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.Commands;


import bgu.spl181.net.impl.bidi.IService;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.ResultObj;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.ResultObjType;
import bgu.spl181.net.impl.dbClasses.User;
import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;

import java.util.Arrays;

public class REGISTERCommand extends BaseCommand {
    private String ErrorOutput = "ERROR registration failed";
    private String SuccessOutput="ACK registration succeeded";
    private String userName ,password, country;
    private boolean isLogged;
    private IService service;
    private String[] movies;

    public REGISTERCommand(String[] messageArr, UsersJsonHandler usersJsonHandler, IService service,boolean isLogged) {
        super(usersJsonHandler, messageArr);

        this.isLogged=isLogged;
        this.service=service;
    }


    @Override
    public ResultObj execute() {
        if(isLogged)
            return new ResultObj(ResultObjType.ERROR, ErrorOutput);

        if (messageArr.length < 3)
            return new ResultObj(ResultObjType.ERROR,ErrorOutput);

        userName = messageArr[1];
        password = messageArr[2];

            if (messageArr.length > 3) {
                String[] dataBlock = Arrays.copyOfRange(messageArr, 3, messageArr.length);
                String countryBeforeFixing = String.join(" ", dataBlock);
                if (!(countryBeforeFixing.length() >= 11 &&
                        countryBeforeFixing.substring(0, 10).equals("country=" + '"') &&
                        countryBeforeFixing.charAt(countryBeforeFixing.length() - 1) == '"')) {

                    return new ResultObj(ResultObjType.ERROR,ErrorOutput);
                }
                country = countryBeforeFixing.substring(countryBeforeFixing.indexOf('"'), countryBeforeFixing.lastIndexOf('"'));
            }

            if (usersJsonHandler.getUser(userName) != null) {

                User userToAdd = new User();
                userToAdd.setUsername(userName);
                userToAdd.setPassword(password);

                if (country != null)
                    userToAdd.setCountry(country);


                usersJsonHandler.addRecord(userToAdd);
                return new ResultObj(ResultObjType.ERROR, ErrorOutput);
            }

        return new ResultObj(ResultObjType.ACK, SuccessOutput);
    }
}

