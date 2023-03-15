package top.jtning.test;

import top.jtning.rpc.api.HelloService;
import top.jtning.rpc.serializer.ProtobufSerializer;
import top.jtning.rpc.transport.RpcServer;
import top.jtning.rpc.transport.netty.server.NettyServer;

public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
//        ServiceProvider serviceProvider = new ServiceProviderImpl();
//        serviceProvider.addServiceProvider(helloService);
        RpcServer server = new NettyServer("127.0.0.1", 9999);
        server.setSerializer(new ProtobufSerializer());
        server.publishService(helloService, HelloService.class);
    }
}
