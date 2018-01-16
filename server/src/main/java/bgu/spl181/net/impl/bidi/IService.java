package bgu.spl181.net.impl.bidi;

import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.ResultObj;

import java.util.Map;

public interface IService {

    ResultObj handle(String username, String[] parameters);

    boolean validateDataBlock(Map<String, String> dataBlock);

}