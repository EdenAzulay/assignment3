package bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.commands;

import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;

import java.util.concurrent.ConcurrentHashMap;

public class ACKCommand extends BaseCommand {
    ConcurrentHashMap loggedUsers;

    public ACKCommand(String[] messageArr, UsersJsonHandler usersJsonHandler, ConcurrentHashMap loggedUsers) {
        super(usersJsonHandler,messageArr);
    }

    @Override
    String execute() {
        return null;
    }
}
