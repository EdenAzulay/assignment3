package bgu.spl181.net.impl.bidi.BBService;

import bgu.spl181.net.impl.bidi.IService;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.Result;
import bgu.spl181.net.impl.dbClasses.Movie;
import bgu.spl181.net.impl.dbClasses.MoviesJsonHandler;
import bgu.spl181.net.impl.dbClasses.User;
import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;

import java.util.Arrays;
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
    public String handle(String username, String[] messageArr) {
        String userName=username;
        boolean isAdmin=usersJsonHandler.getUser(username).getType().equals("admin");

        String requestType=messageArr[1];
        if(requestType.equals("balance") && messageArr.length>2)
            requestType=requestType+ " " + messageArr[2];

        switch (requestType){
            //all users permission

            case "balance info" :{
                return "ACK balance "+ usersJsonHandler.getUser(username).getBalance();
            }

            case "balance add" :{
                if(messageArr.length==3) {
                    int balanceToAdd = Integer.parseInt(messageArr[3]);
                    int currBalance= Integer.parseInt(usersJsonHandler.getUser(username).getBalance());
                    int newBalance = balanceToAdd+currBalance;
                    String newBalanceStr= Integer.toString(newBalance);
                    usersJsonHandler.getUser(username).setBalance(newBalanceStr);

                    return "ACK balance "+newBalanceStr+" added "+messageArr[3];
                }
            }

            case "info" :{
                if(messageArr.length>2) {
                    String[] movieNameArr = Arrays.copyOfRange(messageArr,2,messageArr.length);
                    String movieName = String.join(" ", movieNameArr);
                    String finalMovieName= movieName.substring(1,movieName.length()-1);
                    Movie movie = moviesJsonHandler.getMovie(movieName);
                    if (movie == null)
                        return "ERROR request "+requestType +" failed";

                    String movieAmount= movie.getAvailbleAmount();
                    String moviePrice=movie.getMoviePrice();
                    String[] movieBannedCountriesArr=movie.getBannedCountries();
                    String movieBannedCountries= String.join(",",movieBannedCountriesArr);

                    return "ACK info "+movieName+" "+movieAmount+" "+moviePrice+" "+movieBannedCountries;


                }
                break;}

            case "rent" :{
                String[] movieNameArr = Arrays.copyOfRange(messageArr,2,messageArr.length);
                String movieName = String.join(" ", movieNameArr);

                boolean rentIsConfirm = true;
                //validate movie exist
                Movie movie = moviesJsonHandler.getMovie(movieName);
                if (movie == null)
                    rentIsConfirm = false;

                //user have enough money
                User user= usersJsonHandler.getUser(username);
                String userBalanceStr = user.getBalance();
                int userBalance = Integer.parseInt(userBalanceStr);
                int moviePrice = Integer.parseInt(movie.getMoviePrice());
                if (userBalance >= moviePrice)
                    rentIsConfirm = false;

                //there is at least one copy from this movie
                String movieAmountStr= movie.getAvailbleAmount();
                int movieAmount = Integer.parseInt(movieAmountStr);
                if (movieAmount < 1)
                    rentIsConfirm = false;

                //validate the movie isn't banned in the user's country
                String[] movieBannedCountriesArr=movie.getBannedCountries();
                String userCountry = user.getCountry();
                for (String country: movieBannedCountriesArr) {
                    if (country == userCountry)
                        rentIsConfirm = false;
                }

                //validate the user is not already renting this movie
                String[] userMoviesArr = user.getMovies();
                for (String userMovie: userMoviesArr) {
                    if (userMovie == movieName)
                        rentIsConfirm = false;
                }

                if (!rentIsConfirm)
                    return "ERROR request "+requestType +" failed";

                //update user balance
                user.setBalance(Integer.toString(userBalance - moviePrice));

                //add movie to the userMovies
                String[] userMoviesNew = Arrays.copyOf(userMoviesArr, userMoviesArr.length+1);
                userMoviesNew[userMoviesNew.length] = movieName;
                user.setMovies(userMoviesNew);

                //update movie available amount
                String movieAmountNew = Integer.toString(movieAmount--);
                movie.setAvailbleAmount(movieAmountNew);

                //todo : broadcast

                return
            }
            case "return" :{
                break;
            }
            //admin permission
            case "addMovie" :{
                break;
            }
            case "remmovie" :{
                break;
            }
            case "changePrice" :{
                break;
            }
            //invalid input
            default:

        }

        return;
    }

    private String handleRequest(String userName, boolean isAdmin, String requestType, String[]messageArr){
        String result=null;

        return result;
    }

    @Override
    public boolean validateDataBlock(Map<String, String> dataBlock) {
        return false;
    }
    //TODO- complete this function.
}
