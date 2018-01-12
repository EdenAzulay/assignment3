package bgu.spl181.net.impl.bidi.BBService;

import bgu.spl181.net.impl.bidi.IService;
import bgu.spl181.net.impl.bidi.UserServiceTextBasedProtocol.Result;

import java.util.List;
import java.util.Map;

public class MovieRentalService implements IService {
    @Override
    public Result handle(String name, String username, List<String> parameters) {
        return null;
    }

    @Override
    public boolean validateDataBlock(Map<String, String> dataBlock) {
        return false;
    }
}
