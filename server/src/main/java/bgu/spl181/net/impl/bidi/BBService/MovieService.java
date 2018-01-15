package bgu.spl181.net.impl.bidi.BBService;

import bgu.spl181.net.impl.bidi.IService;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.Result;
import bgu.spl181.net.impl.dbClasses.MoviesJsonHandler;
import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;
import java.util.Map;

public class MovieService implements IService {
    //TODO-check the field COUNTRY_ATRIBUTE

    private UsersJsonHandler usersJsonHandler;
    private MoviesJsonHandler moviesJsonHandler;

    public MovieService(MoviesJsonHandler moviesJsonHandler, UsersJsonHandler usersJsonHandler){
        this.moviesJsonHandler=moviesJsonHandler;
        this.usersJsonHandler=usersJsonHandler;
    }

    @Override
    public String handle(String name, String username, String[] parameters) {
        return null; //TODO-should be null return or implement all request options here?
    }

    @Override
    public boolean validateDataBlock(Map<String, String> dataBlock) {
        return false;
    }
    //TODO- complete this function.
}
