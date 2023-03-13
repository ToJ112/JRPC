package top.jtning.test;

import top.jtning.rpc.RpcServer;
import top.jtning.rpc.api.HelloService;
import top.jtning.rpc.netty.server.NettyServer;
import top.jtning.rpc.registry.DefaultServiceRegistry;
import top.jtning.rpc.registry.ServiceRegistry;

public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        RpcServer server = new NettyServer();
        server.start(9999);
    }
}
