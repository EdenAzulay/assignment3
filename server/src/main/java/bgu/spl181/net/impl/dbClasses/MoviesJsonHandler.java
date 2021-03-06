package bgu.spl181.net.impl.dbClasses;


import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;

public class MoviesJsonHandler extends JsonHandler<Movie> {
    private final String ROOT_KEY = "movies";

    public MoviesJsonHandler(String path, ReadWriteLock readWriteLock) {
        super(path, readWriteLock);
    }

    @Override
    public ArrayList<Movie> readAll() {
        JsonObject jsonObject = this.getJsonObject();

        Type type = new TypeToken<ArrayList<Movie>>() {
        }.getType();

        // Convert JSON to Java Object
        ArrayList<Movie> movies = this.gson.fromJson(jsonObject.get(ROOT_KEY), type);

        return movies;
    }

    @Override
    public void addRecord(Movie record) {
        ArrayList<Movie> movies = this.readAll();

        movies.add(record);

        this.writeJsonObject(movies, ROOT_KEY);
    }

    public Movie getMovie(String movieName) {
        Movie query = new Movie();
        query.setMovieName(movieName);
        return this.getEntity(query);
    }

    public boolean isMovieExists(String movieName){
        return getMovie(movieName) != null;
    }

    public void removeMovie(String name){
        ArrayList<Movie> arr =this.readAll();
        arr.remove(this.getMovie(name));
        this.writeJsonObject(arr,this.ROOT_KEY);
    }
}
