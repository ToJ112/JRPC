package top.jtning.test;

import top.jtning.rpc.RpcClient;
import top.jtning.rpc.RpcClientProxy;
import top.jtning.rpc.api.HelloObject;
import top.jtning.rpc.api.HelloService;
import top.jtning.rpc.netty.client.NettyClient;
import top.jtning.rpc.serializer.HessianSerializer;

public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        client.setSerializer(new HessianSerializer());
        RpcClientProxy clientProxy = new RpcClientProxy(client);
        HelloService helloService = clientProxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(12, "This is a message");
        String res = helloService.hello(helloObject);
        System.out.println(res);
    }
}
