package top.jtning.test;

import top.jtning.rpc.annotation.ServiceScan;
import top.jtning.rpc.serializer.CommonSerializer;
import top.jtning.rpc.transport.RpcServer;
import top.jtning.rpc.transport.socket.server.SocketServer;
@ServiceScan
public class SocketTestServer {
    public static void main(String[] args) {
        RpcServer server = new SocketServer("127.0.0.1", 9998, CommonSerializer.HESSIAN_SERIALIZER);
        server.start();
    }
}
