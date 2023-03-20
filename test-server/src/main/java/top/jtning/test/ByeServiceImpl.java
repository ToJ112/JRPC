package top.jtning.test;

import top.jtning.rpc.annotation.Service;
import top.jtning.rpc.api.ByeService;

@Service
public class ByeServiceImpl implements ByeService {
    @Override
    public String bye(String name) {
        return "bye, " + name;
    }
}
