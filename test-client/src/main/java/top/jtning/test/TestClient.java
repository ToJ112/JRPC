package top.jtning.test;

import top.jtning.rpc.api.HelloObject;
import top.jtning.rpc.api.HelloService;
import top.jtning.rpc.RpcClientProxy;
import top.jtning.rpc.serializer.HessianSerializer;
import top.jtning.rpc.socket.client.SocketClient;

public class TestClient {
    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1",9000);
        client.setSerializer(new HessianSerializer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(12, "THIS IS A MESSAGE");
        String res = helloService.hello(helloObject);
        System.out.println(res);
    }
}
