package bgu.spl181.net.impl.dbClasses;

import java.util.Map;


import java.util.Objects;

//todo:: fix

public class User {
    private String username;
    private String type;
    private String password;
    private String country;
    private String[] movies;
    private String balance;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getType() {
        return this.type;
    }

    public String getPassword() {
        return this.password;
    }

    public String getCountry() {
        return this.country;
    }

    public String getBalance() {
        return this.balance;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User))
            return false;

        User other = (User) obj;

        if (!Objects.equals(other.getUsername(), this.getUsername()))
            return false;

        return true;
    }

    public String[] getMovies() {
        return movies;
    }

    public void setMovies(String[] movies) {
        this.movies = movies;
    }
}
