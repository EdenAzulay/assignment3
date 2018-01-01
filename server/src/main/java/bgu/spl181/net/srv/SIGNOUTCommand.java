package bgu.spl181.net.srv;

import bgu.spl181.net.impl.rci.BaseCommand;
import bgu.spl181.net.impl.rci.Command;

import java.io.Serializable;

public class SIGNOUTCommand<String> implements BaseCommand<String> {
    @Override
    public Serializable execute(String arg) {
        return null;
    }
}
