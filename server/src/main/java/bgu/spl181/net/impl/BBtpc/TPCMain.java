package bgu.spl181.net.impl.BBtpc;

import bgu.spl181.net.impl.bidi.BBService.MovieService;
import bgu.spl181.net.impl.bidi.IService;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.LineMessageEncoderDecoder;
import bgu.spl181.net.impl.dbClasses.MoviesJsonHandler;
import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;
import bgu.spl181.net.srv.Server;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.UserServiceTextBasedProtocol;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TPCMain {

    public static void main(String[] args) {
        int port=0;
        try{
            //port=Integer.parseInt(args[0]);
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }

        String usersDbPath = "Database/Users.json";
        String moviesDbPath = "Database/Movies.json";
        ReadWriteLock movieReadWriteLock = new ReentrantReadWriteLock();
        ReadWriteLock userReadWriteLock = new ReentrantReadWriteLock();

        ConcurrentHashMap<String,String> loggedUsers=new ConcurrentHashMap();
        UsersJsonHandler usersJsonHandler=new UsersJsonHandler(usersDbPath,userReadWriteLock);
        MoviesJsonHandler moviesJsonHandler=new MoviesJsonHandler(moviesDbPath,movieReadWriteLock);
        IService movieService=new MovieService(moviesJsonHandler,usersJsonHandler);


        Server.threadPerClient(
                port,
                () -> new UserServiceTextBasedProtocol(usersJsonHandler, loggedUsers, movieService),
                LineMessageEncoderDecoder::new).serve();
    }
}
