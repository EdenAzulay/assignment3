package bgu.spl181.net.impl.rci;

import java.io.Serializable;

public interface BaseCommand<String> extends Serializable {

    Serializable execute(String arg);
}
