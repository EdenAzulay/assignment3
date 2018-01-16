package bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol;

public class ResultObj {
    private String response;
    private String broadcast;
    private ResultObjType resultObjType ;

    public ResultObj(ResultObjType resultType, String response) {
        this.resultObjType =resultType;
        this.response = response;
        this.broadcast = null;
    }

    public ResultObj(ResultObjType resultObjType , String response, String broadcast) {
        this(resultObjType, response);
        this.broadcast = broadcast;
    }

    public boolean hasBroadcase() {
        return this.broadcast != null;
    }

    public ResultObjType getType() {
        return this.resultObjType;
    }

    public String getResponse() {
        return response;
    }

    public String getBroadcast() {
        return broadcast;
    }
}
