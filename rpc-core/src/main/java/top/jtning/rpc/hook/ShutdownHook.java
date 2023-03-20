package top.jtning.rpc.hook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jtning.rpc.factory.ThreadPoolFactory;
import top.jtning.rpc.util.NacosUtil;

public class ShutdownHook {
    private static final Logger logger = LoggerFactory.getLogger(ShutdownHook.class);
    private static final ShutdownHook shutdownHook = new ShutdownHook();

    public static ShutdownHook getShutdownHook() {
        return shutdownHook;
    }
    /*
    Runtime 对象是 JVM 虚拟机的运行时环境，
    调用其 addShutdownHook 方法增加一个钩子函数，
    创建一个新线程调用 clearRegistry 方法完成注销工作。
    这个钩子函数会在 JVM 关闭之前被调用。
     */
    public void addClearAllHook() {
        logger.info("all services will be deregisterd after close");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            NacosUtil.clearRegistry();
            ThreadPoolFactory.shutDownAll();
        }));
    }
}
