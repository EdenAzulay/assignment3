package bgu.spl181.net.impl.dbClasses;

public class Movie {
    private String movieName;
    private String movieID;
    private String moviePrice;
    private String[] bannedCountries;
    private String availbleAmount;
    private String totalAmount;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getMoviePrice() {
        return moviePrice;
    }

    public void setMoviePrice(String moviePrice) {
        this.moviePrice = moviePrice;
    }

    public String[] getBannedCountries() {
        return bannedCountries;
    }

    public void setBannedCountries(String[] bannedCountries) {
        this.bannedCountries = bannedCountries;
    }

    public String getAvailbleAmount() {
        return availbleAmount;
    }

    public void setAvailbleAmount(String availbleAmount) {
        this.availbleAmount = availbleAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
