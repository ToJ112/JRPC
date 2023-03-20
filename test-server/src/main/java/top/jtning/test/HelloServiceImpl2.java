package top.jtning.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jtning.rpc.annotation.Service;
import top.jtning.rpc.api.HelloObject;
import top.jtning.rpc.api.HelloService;
@Service
public class HelloServiceImpl2 implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl2.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("receive message：{}", object.getMessage());
        return "Current service from socket";
    }
}
