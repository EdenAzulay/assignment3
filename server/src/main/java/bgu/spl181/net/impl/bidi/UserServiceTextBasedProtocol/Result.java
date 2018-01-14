package bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol;

public class Result {
    private String response;
    private String broadcast;
    private String resultType ;

    public Result(String resultType, String response) {
        this.resultType =resultType;
        this.response = response;
        this.broadcast = null;
    }

    public Result(String resultType , String response, String broadcast) {
        this(resultType, response);
        this.broadcast = broadcast;
    }

    public boolean hasBroadcase() {
        return this.broadcast != null;
    }

    public String getType() {
        return this.resultType;
    }

    public String getResponse() {
        return response;
    }

    public String getBroadcast() {
        return broadcast;
    }
}
