package bgu.spl181.net.impl.bidi;

import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.Result;

import java.util.List;
import java.util.Map;

public interface IService {
    Result handle(String name, String username, String[] parameters);

    boolean validateDataBlock(Map<String, String> dataBlock);
}