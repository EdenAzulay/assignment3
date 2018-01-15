package bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.Commands;


import bgu.spl181.net.impl.dbClasses.User;
import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;

import java.util.Arrays;

public class REGISTERCommand extends BaseCommand {
    private String output = "ERROR registration failed";
    private String userName ,password, country;
    private String[] movies;

    public REGISTERCommand(String[] messageArr, UsersJsonHandler usersJsonHandler) {
        super(usersJsonHandler, messageArr);
    }


    @Override
    public String execute() {
        if (messageArr.length >= 3) {
            userName = messageArr[1];
            password = messageArr[2];

            if (messageArr.length > 3) {
                String[] dataBlock = Arrays.copyOfRange(messageArr, 3,messageArr.length);
                String countryBeforeFixing = String.join(" ", dataBlock);
                if (!(countryBeforeFixing.length() >= 11 &&
                        countryBeforeFixing.substring(0, 10).equals("country=" + '"') &&
                        countryBeforeFixing.charAt(countryBeforeFixing.length() - 1) == '"')) {

                    return output;
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
                    output = "ACK registration succeeded";
                }

            }


        return output;

    }
}

