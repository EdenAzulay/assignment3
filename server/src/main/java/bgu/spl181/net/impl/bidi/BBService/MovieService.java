package bgu.spl181.net.impl.bidi.BBService;

import bgu.spl181.net.impl.bidi.IService;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.ResultObj;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.ResultObjType;
import bgu.spl181.net.impl.dbClasses.Movie;
import bgu.spl181.net.impl.dbClasses.MoviesJsonHandler;
import bgu.spl181.net.impl.dbClasses.User;
import bgu.spl181.net.impl.dbClasses.UsersJsonHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MovieService implements IService {
    private final String Country = "country";

    private UsersJsonHandler usersJsonHandler;
    private MoviesJsonHandler moviesJsonHandler;

    public MovieService(MoviesJsonHandler moviesJsonHandler, UsersJsonHandler usersJsonHandler) {
        this.moviesJsonHandler = moviesJsonHandler;
        this.usersJsonHandler = usersJsonHandler;
    }

    @Override
    public ResultObj handle(String username, String[] messageArr) {
        String userName = username;
        boolean isAdmin = usersJsonHandler.getUser(username).getType().equals("admin");

        String requestType = messageArr[1];
        if (requestType.equals("balance") && messageArr.length > 2)
            requestType = requestType + " " + messageArr[2];

        switch (requestType) {
            //all users permission

            case "balance info": {
                return new ResultObj(ResultObjType.ACK,"ACK balance " + usersJsonHandler.getUser(username).getBalance());
            }

            case "balance add": {
                if (messageArr.length == 3) {
                    int balanceToAdd = Integer.parseInt(messageArr[3]);
                    int currBalance = Integer.parseInt(usersJsonHandler.getUser(username).getBalance());
                    int newBalance = balanceToAdd + currBalance;
                    String newBalanceStr = Integer.toString(newBalance);
                    usersJsonHandler.getUser(username).setBalance(newBalanceStr);

                    return new ResultObj(ResultObjType.ACK, "ACK balance " + newBalanceStr + " added " + messageArr[3]);
                }
            }

            case "info": {
                if (messageArr.length > 2) {
                    String[] movieNameArr = Arrays.copyOfRange(messageArr, 2, messageArr.length);
                    String movieName = String.join(" ", movieNameArr);
                    String finalMovieName = movieName.substring(1, movieName.length() - 1);
                    Movie movie = moviesJsonHandler.getMovie(movieName);
                    if (movie == null)
                        return new ResultObj(ResultObjType.ERROR, "ERROR request " + requestType + " failed");

                    String movieAmount = movie.getAvailbleAmount();
                    String moviePrice = movie.getMoviePrice();
                    String[] movieBannedCountriesArr = movie.getBannedCountries();
                    String movieBannedCountries = String.join(",", movieBannedCountriesArr);

                    return new ResultObj(ResultObjType.ACK,"ACK info " + movieName + " " + movieAmount + " " + moviePrice + " " + movieBannedCountries);

                }

            }

            case "rent": {
                String[] movieNameArr = Arrays.copyOfRange(messageArr, 2, messageArr.length);
                String movieNameBeforeFixing = String.join(" ", movieNameArr);

                boolean rentIsConfirm = true;

                //input validating
                if(movieNameBeforeFixing.charAt(0) != '"' || movieNameBeforeFixing.charAt(movieNameBeforeFixing.length()) != '"')
                    return new ResultObj(ResultObjType.ERROR,"ERROR request " + requestType + " failed");

                String movieName = movieNameBeforeFixing.substring(1, movieNameBeforeFixing.length()-1);

                //validate movie exist
                Movie movie = moviesJsonHandler.getMovie(movieName);
                if (movie == null)
                    rentIsConfirm = false;

                //user have enough money
                User user = usersJsonHandler.getUser(username);
                String userBalanceStr = user.getBalance();
                int userBalance = Integer.parseInt(userBalanceStr);
                int moviePrice = Integer.parseInt(movie.getMoviePrice());
                if (userBalance >= moviePrice)
                    rentIsConfirm = false;

                //there is at least one copy from this movie
                String movieAmountStr = movie.getAvailbleAmount();
                int movieAmount = Integer.parseInt(movieAmountStr);
                if (movieAmount < 1)
                    rentIsConfirm = false;

                //validate the movie isn't banned in the user's country
                String[] movieBannedCountriesArr = movie.getBannedCountries();
                String userCountry = user.getCountry();
                for (String country : movieBannedCountriesArr) {
                    if (country == userCountry)
                        rentIsConfirm = false;
                }

                //validate the user is not already renting this movie
                String[] userMoviesArr = user.getMovies();
                for (String userMovie : userMoviesArr) {
                    if (userMovie == movieName)
                        rentIsConfirm = false;
                }

                if (!rentIsConfirm)
                    return new ResultObj(ResultObjType.ERROR,"ERROR request " + requestType + " failed");

                //update user balance
                user.setBalance(Integer.toString(userBalance - moviePrice));

                //add movie to the userMovies
                String[] userMoviesNew = Arrays.copyOf(userMoviesArr, userMoviesArr.length + 1);
                userMoviesNew[userMoviesNew.length] = movieName;
                user.setMovies(userMoviesNew);

                //update movie available amount
                String movieAmountNew = Integer.toString(movieAmount--);
                movie.setAvailbleAmount(movieAmountNew);

                return new ResultObj(ResultObjType.ACK, "ACK request "+ requestType+" success",
                            "BROADCAST movie "+ movieName +" "+ movieAmountNew+" "+ moviePrice);
            }

            case "return": {
                if (messageArr.length < 3)
                    return new ResultObj(ResultObjType.ERROR,"ERROR request " + requestType + " failed");


                    boolean returnIsConfirm = false;
                    User user = usersJsonHandler.getUser(username);

                String[] movieNameArr = Arrays.copyOfRange(messageArr, 2, messageArr.length);
                String movieNameBeforeFixing = String.join(" ", movieNameArr);

                //input validating
                if(movieNameBeforeFixing.charAt(0) != '"' || movieNameBeforeFixing.charAt(movieNameBeforeFixing.length()) != '"')
                    return new ResultObj(ResultObjType.ERROR,"ERROR request " + requestType + " failed");

                String movieToReturn = movieNameBeforeFixing.substring(1, movieNameBeforeFixing.length()-1);

                    //check if the user has that movie
                    for (String userMovie : user.getMovies()) {
                        if (userMovie == movieToReturn)
                            returnIsConfirm = true;
                    }

                    //check if the movie exist
                    Movie movie = moviesJsonHandler.getMovie(movieToReturn);
                    if (movie != null)
                        returnIsConfirm = true;

                    if (!returnIsConfirm)
                        return new ResultObj(ResultObjType.ERROR,"ERROR request " + requestType + " failed");

                //remove movie from the userMovies
                String[] userMoviesArrBeforeFixing = user.getMovies();
                String[] userMoviesArr = new String[userMoviesArrBeforeFixing.length-1];
                int j = 0;
                for (int i=0; i< userMoviesArrBeforeFixing.length; i++){
                    if (! userMoviesArrBeforeFixing[i].equals(movieToReturn)){
                        userMoviesArr[j] = userMoviesArrBeforeFixing[i];
                        j++;
                    }
                }
                user.setMovies(userMoviesArr);

                //update movie available amount
                int movieAmout = Integer.parseInt(movie.getAvailbleAmount());
                movieAmout++;
                String movieAmountNew = Integer.toString(movieAmout);
                movie.setAvailbleAmount(movieAmountNew);


                return new ResultObj(ResultObjType.ACK,"ACK return " + movieToReturn + " success",
                            "BROADCAST movie "+ '"' + movieToReturn+ '"' + " " + movie.getAvailbleAmount() + " " + movie.getMoviePrice());
            }
            //admin permission
            case "addMovie": {
                if (!isAdmin)
                    return new ResultObj(ResultObjType.ERROR, "ERROR request " + requestType + " failed");


                String movieName = "";
                String amountStr, priceStr;
                String[] bannedCountries;
                int movieNameFound = 0; // stop in 2
                try {
                    for (int i = 2; i < messageArr.length; i++) {
                        if (movieNameFound<2 && messageArr[i].indexOf('"')!=-1) // if contain '"'
                            movieNameFound++;

                        if (movieNameFound<2)
                            movieName = movieName + " "+ messageArr[i];
                        if

                        else{
                            amountStr =

                        }





                    }


                    ///
                    String messageListBeforeFixing = Arrays.toString(messageArr);
                    String messageList=messageListBeforeFixing.substring(messageListBeforeFixing.indexOf('"'),messageListBeforeFixing.length());
                    String movieName=messageList.substring(1,messageList.indexOf('"'));
                    String amountStr=messageList.


                }
                catch (Exception e) {
                    return new ResultObj(ResultObjType.ERROR, "ERROR request " + requestType + " failed");
                }
            }

            case "remmovie": {
                if (!isAdmin)
                    return new ResultObj(ResultObjType.ERROR,"ERROR request " + requestType + " failed");

                break;
            }
            case "changePrice": {
                if (!isAdmin)
                    return new ResultObj(ResultObjType.ERROR,"ERROR request " + requestType + " failed");

                break;
            }
            //invalid input
            default:

        }

        return;
    }

    @Override
    public boolean validateDataBlock(Map<String, String> dataBlock) {
        boolean bol = true;

        if (dataBlock.size() != 1) bol = false;

        if (!dataBlock.containsKey(Country)) bol = false;

        return bol;
    }
}
