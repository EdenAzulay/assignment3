package bgu.spl181.net.impl.BBreactor;

public class ReactorMain {
    public static void main(String[] args) {
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return;
        }

        //TODO-finish this.
    }
}
