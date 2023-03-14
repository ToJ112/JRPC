package top.jtning.rpc.transport.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jtning.rpc.entity.RpcRequest;
import top.jtning.rpc.entity.RpcResponse;
import top.jtning.rpc.provider.ServiceProvider;
import top.jtning.rpc.handler.RequestHandler;
import top.jtning.rpc.serializer.CommonSerializer;
import top.jtning.rpc.transport.socket.util.ObjectReader;
import top.jtning.rpc.transport.socket.util.ObjectWriter;

import java.io.*;
import java.net.Socket;

public class RequestHandlerThread implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);
    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceProvider serviceProvider;
    private CommonSerializer serializer;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceProvider serviceProvider, CommonSerializer serializer) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceProvider = serviceProvider;
        this.serializer = serializer;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            RpcRequest rpcRequest = (RpcRequest) ObjectReader.readObject(inputStream);
            String interfaceName = rpcRequest.getInterfaceName();
            Object service = serviceProvider.getServiceProvider(interfaceName);
            Object result = requestHandler.handle(rpcRequest, service);
            RpcResponse<Object> response = RpcResponse.success(result, rpcRequest.getRequestId());
            ObjectWriter.writeObject(outputStream, response, serializer);
        } catch (IOException e) {
            logger.error("An error occurred while calling or sending: ", e);
        }
    }
}
