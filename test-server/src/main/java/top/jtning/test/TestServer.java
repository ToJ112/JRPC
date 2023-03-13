package top.jtning.test;

import top.jtning.rpc.api.HelloService;
import top.jtning.rpc.registry.DefaultServiceRegistry;
import top.jtning.rpc.registry.ServiceRegistry;
import top.jtning.rpc.socket.server.SocketServer;

public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.start(9000);
    }
}
