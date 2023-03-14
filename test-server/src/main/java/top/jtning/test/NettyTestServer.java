package top.jtning.test;

import top.jtning.rpc.RpcServer;
import top.jtning.rpc.api.HelloService;
import top.jtning.rpc.netty.server.NettyServer;
import top.jtning.rpc.registry.DefaultServiceRegistry;
import top.jtning.rpc.registry.ServiceRegistry;
import top.jtning.rpc.serializer.ProtobufSerializer;

public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        RpcServer server = new NettyServer();
        server.setSerializer(new ProtobufSerializer());
        server.start(9999);
    }
}
