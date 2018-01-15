package bgu.spl181.net.impl.dbClasses;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;

public abstract class JsonHandler<T> {
    protected Gson gson;
    protected String path;
    protected ReadWriteLock readWriteLock;

    public JsonHandler(String path, ReadWriteLock readWriteLock) {
        this.path = path;
        this.gson = new Gson();
        this.readWriteLock = readWriteLock;
    }
    public abstract void addRecord(T record);

    public abstract ArrayList<T> readAll();

    public boolean isRecordExists(T record) {
        boolean output = false;
        for (T current : this.readAll()) {
            if (current.equals(record))
                output = true;
        }
        return output;
    }

    protected JsonObject getJsonObject(){
        readWriteLock.readLock().lock();

        try (Reader reader = new FileReader(this.path)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            return jsonObject;

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        catch (Exception e) {
        } finally {
            readWriteLock.readLock().unlock();
        }

        return null;
    }

    protected void writeJsonObject(ArrayList<T> records, String key) {
        readWriteLock.writeLock().lock();

        try (Writer writer = new FileWriter(this.path)) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add(key, gson.toJsonTree(records));
            gson.toJson(jsonObject, writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    protected T getEntity(T entity) {
        for (T curr : this.readAll()) {
            if (curr == entity) return curr;
        }

        return null;
    }
}
