package bgu.spl181.net.srv;

import java.io.Serializable;

public class ERRORCommand implements BaseCommand<String> {
    @Override
    public Serializable execute(String arg) {
        return null;
    }
}

