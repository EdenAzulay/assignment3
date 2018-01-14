package bgu.spl181.net.srv;

import bgu.spl181.net.api.MessageEncoderDecoder;
import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.impl.BBreactor.ConnectionsImpl;
import bgu.spl181.net.srv.bidi.BlockingConnectionHandler;
import bgu.spl181.net.srv.bidi.ConnectionsImpl;
//import bgu.spl181.net.impl.BBtpc.ConnectionsImpl;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Supplier;

public class TPCServer<T> implements Server<T> {
    private int port;
    private Supplier<BidiMessagingProtocol<T>> protocolFactory;
    private Supplier<MessageEncoderDecoder<T>> encoderDecoderFactory;
    private ServerSocket sock;


//    private Gson gson;

    public TPCServer(int port, Supplier<BidiMessagingProtocol<T>> protocolFactory, Supplier<MessageEncoderDecoder<T>> encoderDecoderFactory) {
        this.port = port;
        this.protocolFactory = protocolFactory;
        this.encoderDecoderFactory = encoderDecoderFactory;
        this.sock = null;
        //gson = new GsonBuilder().create();
    }

    @Override
    public void serve() {
        try (ServerSocket serverSock = new ServerSocket(port)) {
            //just to be able to close
            this.sock = serverSock;
            System.out.println("Server started");

            //create ConnectionsImpl
            ConnectionsImpl<T> connections = new ConnectionsImpl<>();

            //create DBs
            //private static Gson gson = new GsonBuilder().disableHtmlEscaping().create();


            while (!Thread.currentThread().isInterrupted()) {
                int availableUserId = connections.generateConnectionId();
                //waiting for client
                Socket clientSock = serverSock.accept();
                //creating protocol
                BidiMessagingProtocol<T> messagingProtocol = protocolFactory.get();
                messagingProtocol.start(availableUserId, connections);
                //creating hadler
                BlockingConnectionHandler<T> handler = new BlockingConnectionHandler<>(clientSock, encoderDecoderFactory.get(), messagingProtocol);
                //adding connection
                connections.connectClient(handler);
                this.execute(handler);
            }
        } catch (IOException ex) {
        }

        System.out.println("server closed!!!");
    }

    @Override
    public void close() throws IOException {
        if (sock != null)
            sock.close();
    }

    private void execute(BlockingConnectionHandler<T> handler) {
        new Thread(handler).start();
    }
}
