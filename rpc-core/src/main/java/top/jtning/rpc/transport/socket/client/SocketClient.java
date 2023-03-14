package top.jtning.rpc.transport.socket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jtning.rpc.transport.RpcClient;
import top.jtning.rpc.entity.RpcRequest;
import top.jtning.rpc.entity.RpcResponse;
import top.jtning.rpc.enumeration.ResponseCode;
import top.jtning.rpc.enumeration.RpcError;
import top.jtning.rpc.exception.RpcException;
import top.jtning.rpc.serializer.CommonSerializer;
import top.jtning.rpc.transport.socket.util.ObjectReader;
import top.jtning.rpc.transport.socket.util.ObjectWriter;
import top.jtning.rpc.util.RpcMessageChecker;

import java.io.*;
import java.net.Socket;

public class SocketClient implements RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);
    private final String host;
    private final int port;
    private CommonSerializer serializer;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object sendRequest(RpcRequest rpcRequest) {
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        // 输入参数校验
        if (rpcRequest == null || host == null || host.isEmpty()) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }
        try (Socket socket = new Socket(host, port)) {
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            ObjectWriter.writeObject(outputStream, rpcRequest, serializer);
            Object obj = ObjectReader.readObject(inputStream);
            RpcResponse rpcResponse = (RpcResponse) obj;
            if (rpcResponse == null) {
                logger.error("service {} invocation failed", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service: " + rpcRequest.getInterfaceName());
            }
            if (rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()) {
                logger.error("service {} invocation failed, response {}", rpcRequest.getInterfaceName(), rpcResponse.getStatusCode());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service: " + rpcRequest.getInterfaceName());
            }
            RpcMessageChecker.check(rpcRequest,rpcResponse);
            return rpcResponse.getData();
        } catch (IOException e) {
            logger.error("调用时有错误发生：", e);
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE);
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }

}
