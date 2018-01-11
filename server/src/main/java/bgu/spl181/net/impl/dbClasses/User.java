package bgu.spl181.net.impl.dbClasses;

import java.util.Map;

public class User {
    private String userName;
    private String password;
    private Map<String,String> dataBlock;
    private String[] movies;
    private String balance; //need to cast to int


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> getDataBlock() {
        return dataBlock;
    }

    public void setDataBlock(Map<String, String> dataBlock) {
        this.dataBlock = dataBlock;
    }

    public String[] getMovies() {
        return movies;
    }

    public void setMovies(String[] movies) {
        this.movies = movies;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
