package top.jtning.test;

import lombok.val;
import top.jtning.rpc.api.HelloService;
import top.jtning.rpc.server.RpcServer;

public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9000);
    }
}
