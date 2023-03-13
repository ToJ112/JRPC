package top.jtning.test;

import top.jtning.rpc.api.HelloService;
import top.jtning.rpc.registry.DefaultServiceRegistry;
import top.jtning.rpc.registry.ServiceRegistry;
import top.jtning.rpc.server.RpcServer;

public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }
}
