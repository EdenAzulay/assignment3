package bgu.spl181.net.impl.dbClasses;

import bgu.spl181.net.srv.Result;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class DbHandler implements IdbHandler{
    private String usersJsonPath;
    private String moviesJsonPath;
    private ReadWriteLock dbReadWriteLock;

    // RW lock one for each db
    public DbHandler(String usersJsonPath, String moviesJsonPath, ReadWriteLock dbReadWriteLock){
        this.usersJsonPath = usersJsonPath;
        this.moviesJsonPath = moviesJsonPath;
        this.dbReadWriteLock = dbReadWriteLock;
    }

    public void addUser(BBUser user){
        //write lock
        dbReadWriteLock.writeLock().lock();
        try {

            //get all user db)s


            //if user

            //add
        } finally {
            try{
                dbReadWriteLock.readLock().lock();
                dbReadWriteLock.writeLock().unlock();
            } finally {
                dbReadWriteLock.readLock().unlock();
            }

        }



        //commit to db (db)
        dbReadWriteLock.readLock().unlock();


    }


    public List<BBUser> getAllUsers(){
        return null;
    }

    //////////////////////////////////////////////////////
    //////////////// Json handler ////////////////////////
    //////////////////////////////////////////////////////

    private ArrayList<BBUser> getAllUsers(){
        JsonObject jsonObject = this.getJsonObject();

    }


    protected JsonObject getJsonObject() {
        dbReadWriteLock.readLock().lock();

        try (Reader reader = new FileReader(this.usersJsonPath)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            return jsonObject;

        } catch (IOException e) {

        } catch (Exception e) {
        } finally {
            dbReadWriteLock.readLock().unlock();
        }


        return null;
    }





}
