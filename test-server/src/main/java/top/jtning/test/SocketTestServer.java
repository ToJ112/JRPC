package top.jtning.test;

import top.jtning.rpc.api.HelloService;
import top.jtning.rpc.provider.ServiceProviderImpl;
import top.jtning.rpc.provider.ServiceProvider;
import top.jtning.rpc.serializer.HessianSerializer;
import top.jtning.rpc.transport.socket.server.SocketServer;

public class SocketTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceProvider serviceProvider = new ServiceProviderImpl();
        serviceProvider.addServiceProvider(helloService);
        SocketServer server = new SocketServer("127.0.0.1",9998);
        server.setSerializer(new HessianSerializer());
        server.publishService(helloService,HelloService.class);
    }
}
