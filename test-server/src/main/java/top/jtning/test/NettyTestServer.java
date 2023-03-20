package top.jtning.test;

import top.jtning.rpc.annotation.ServiceScan;
import top.jtning.rpc.serializer.CommonSerializer;
import top.jtning.rpc.transport.RpcServer;
import top.jtning.rpc.transport.netty.server.NettyServer;
@ServiceScan
public class NettyTestServer {
    public static void main(String[] args) {
//        HelloService helloService = new HelloServiceImpl();
//        RpcServer server = new NettyServer("127.0.0.1", 9998, CommonSerializer.JSON_SERIALIZER);
//        server.publishService(helloService, HelloService.class);
        RpcServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.PROTOBUF_SERIALIZER);
        server.start();
    }
}
