package top.jtning.test;

import top.jtning.rpc.transport.RpcClient;
import top.jtning.rpc.transport.RpcClientProxy;
import top.jtning.rpc.api.HelloObject;
import top.jtning.rpc.api.HelloService;
import top.jtning.rpc.transport.netty.client.NettyClient;
import top.jtning.rpc.serializer.ProtobufSerializer;

public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        client.setSerializer(new ProtobufSerializer());
        RpcClientProxy clientProxy = new RpcClientProxy(client);
        HelloService helloService = clientProxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(12, "This is a message");
        String res = helloService.hello(helloObject);
        System.out.println(res);
    }
}
