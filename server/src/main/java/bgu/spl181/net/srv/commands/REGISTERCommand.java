package bgu.spl181.net.srv.commands;


import bgu.spl181.net.impl.dbClasses.User;
import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;

import java.util.Arrays;

//REGISTER <username> <password> [Data block,…]
/*
      "username": "john",
      "type": "admin",
      "password": "potato",
      "country": "united states",
      "movies": [],
      "balance": "0"
 */
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
                String[] dataBlock = Arrays.copyOf(messageArr, 3);
                String countryBeforeFixing = String.join(" ", dataBlock);
                if (!(countryBeforeFixing.length() >= 11 &&
                        countryBeforeFixing.substring(0, 10).equals("country=" + '"') &&
                        countryBeforeFixing.charAt(countryBeforeFixing.length() - 1) == '"')) {

                    return output;
                }
                country = countryBeforeFixing.substring(countryBeforeFixing.indexOf('"'), countryBeforeFixing.lastIndexOf('"'));
            }

                //check if exists
                if (usersJsonHandler.getUser(userName) != null) {

                    //add to db
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

