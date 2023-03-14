package top.jtning.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jtning.rpc.api.HelloObject;
import top.jtning.rpc.api.HelloService;

public class HelloServiceImpl implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("receive message：{}", object.getMessage());
        return "invoke result: id =" + object.getId();
    }
}
