package top.jtning.test;

import top.jtning.rpc.transport.RpcServer;
import top.jtning.rpc.api.HelloService;
import top.jtning.rpc.transport.netty.server.NettyServer;
import top.jtning.rpc.provider.ServiceProviderImpl;
import top.jtning.rpc.provider.ServiceProvider;
import top.jtning.rpc.serializer.ProtobufSerializer;

public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceProvider serviceProvider = new ServiceProviderImpl();
        serviceProvider.addServiceProvider(helloService);
        RpcServer server = new NettyServer();
        server.setSerializer(new ProtobufSerializer());
        server.start(9999);
    }
}
