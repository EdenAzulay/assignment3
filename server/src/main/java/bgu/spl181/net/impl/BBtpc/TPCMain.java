package bgu.spl181.net.impl.BBtpc;

import bgu.spl181.net.impl.dbClasses.DbHandler;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TPCMain {
    public static DbHandler dbHandler;

    public static void main(String[] args) {
        //Data bases
        String usersDbPath = "Database/Users.json";
        String moviesDbPath = "Database/Movies.json";

        //Db read/write Locker
        ReadWriteLock dbReadWriteLock = new ReentrantReadWriteLock();

        dbHandler = new DbHandler(usersDbPath, moviesDbPath,dbReadWriteLock);

        //todo: not finished


    }
}
