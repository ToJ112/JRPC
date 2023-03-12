package top.jtning.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class RpcServer {
    public static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
    private final Executor threadPool;

    public RpcServer() {
        // 线程池基本大小为5，最大大小为50，非核心线程空闲时间为60秒，使用有界队列保存任务
        int corePoolSize = 5;
        int maximumPoolSize = 50;
        long keepAliveTime = 60;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workingQueue, Executors.defaultThreadFactory());
    }

    public void register(Object service, int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server is starting up...");
            while (true) {
                Socket socket = serverSocket.accept();
                logger.info("Client connected! IP address is: " + socket.getInetAddress());
                threadPool.execute(new WorkerThread(socket, service));
            }
        } catch (IOException e) {
            logger.error("An error occurred while establishing a connection: ", e);
        } catch (Exception e) {
            logger.error("An error occurred for: ", e);
        }
    }
}
