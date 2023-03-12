package top.jtning.rpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jtning.rpc.entity.RpcRequest;

import java.io.*;
import java.net.Socket;

public class RpcClient {
    public static final Logger logger = LoggerFactory.getLogger(RpcClient.class);


    public Object sendRequest(RpcRequest rpcRequest, String host, int port) {
        // 输入参数校验
        if (rpcRequest == null || host == null || host.isEmpty()) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }
        try (Socket socket = new Socket()) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用时有错误发生：", e);
            return null;
        }
    }
}
