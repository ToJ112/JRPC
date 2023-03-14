package top.jtning.test;

import top.jtning.rpc.api.HelloService;
import top.jtning.rpc.registry.DefaultServiceRegistry;
import top.jtning.rpc.registry.ServiceRegistry;
import top.jtning.rpc.serializer.HessianSerializer;
import top.jtning.rpc.socket.server.SocketServer;

public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        SocketServer server = new SocketServer(serviceRegistry);
        server.setSerializer(new HessianSerializer());
        server.start(9000);
    }
}
