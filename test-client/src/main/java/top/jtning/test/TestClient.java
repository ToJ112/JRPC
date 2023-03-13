package top.jtning.test;

import top.jtning.rpc.api.HelloObject;
import top.jtning.rpc.api.HelloService;
import top.jtning.rpc.RpcClientProxy;

public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(12, "THIS IS A MESSAGE");
        String res = helloService.hello(helloObject);
        System.out.println(res);
    }
}
