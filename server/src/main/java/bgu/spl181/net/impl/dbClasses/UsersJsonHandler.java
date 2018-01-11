package bgu.spl181.net.impl.dbClasses;

import bgu.spl181.net.impl.bidi.db.JsonDBHandler;
import bgu.spl181.net.impl.bidi.db.entities.users.User;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;


public class UsersJsonHandler extends JsonHandler<User> {
    private final String ROOT_KEY = "users";

    public UsersJsonHandler(String path, ReadWriteLock readWriteLock) {
        super(path, readWriteLock);
    }

    @Override
    public ArrayList<User> readAll() {
        JsonObject jsonObject = this.getJsonObject();

        Type type = new TypeToken<ArrayList<User>>() {
        }.getType();

        // Convert JSON to Java Object
        ArrayList<User> users = this.gson.fromJson(jsonObject.get(ROOT_KEY), type);

        return users;
    }

    @Override
    public void addRecord(User record) {
        ArrayList<User> users = this.readAll();

        users.add(record);

        this.writeJsonObject(users, ROOT_KEY);
    }

    public boolean validateCredentials(String username, String password) {
        User user = this.getUser(username);

        return user != null && Objects.equals(user.getPassword(), password);
    }

    private User getUser(String username) {
        User query = new User();
        query.setUsername(username);
        return this.getEntity(query);
    }
}
