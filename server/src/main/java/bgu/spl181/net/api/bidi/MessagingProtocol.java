package bgu.spl181.net.api.bidi;

public interface MessagingProtocol<T>{

    void process(T message);

    boolean shouldTerminate();

}
