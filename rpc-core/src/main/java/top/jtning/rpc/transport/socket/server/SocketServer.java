package top.jtning.rpc.transport.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jtning.rpc.enumeration.RpcError;
import top.jtning.rpc.exception.RpcException;
import top.jtning.rpc.handler.RequestHandler;
import top.jtning.rpc.provider.ServiceProvider;
import top.jtning.rpc.provider.ServiceProviderImpl;
import top.jtning.rpc.registry.NacosServiceRegistry;
import top.jtning.rpc.registry.ServiceRegistry;
import top.jtning.rpc.serializer.CommonSerializer;
import top.jtning.rpc.transport.RpcServer;
import top.jtning.rpc.util.ThreadPoolFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class SocketServer implements RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);
    private final ExecutorService threadPool;


    private final RequestHandler requestHandler = new RequestHandler();


    private final String host;
    private final int port;
    private final ServiceProvider serviceProvider;
    private final ServiceRegistry serviceRegistry;
    private CommonSerializer serializer;

    //    public SocketServer(ServiceProvider serviceProvider) {
//        this.serviceProvider = serviceProvider;
//        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
//    }
    public SocketServer(String host, int port) {
        this.host = host;
        this.port = port;
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        this.serviceRegistry = new NacosServiceRegistry();
        this.serviceProvider = new ServiceProviderImpl();
    }

    @Override
    public <T> void publishService(Object service, Class<T> serviceClass) {
        if (serializer == null) {
            logger.error("serializer not set");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        serviceProvider.addServiceProvider(service);
        serviceRegistry.register(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
        start();
    }

    public void start() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server is starting up...");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("Consumer connection: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceProvider, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("An error occurred while establishing a connection: ", e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
