package top.jtning.rpc.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jtning.rpc.RpcClient;
import top.jtning.rpc.codec.CommonDecoder;
import top.jtning.rpc.codec.CommonEncoder;
import top.jtning.rpc.entity.RpcRequest;
import top.jtning.rpc.entity.RpcResponse;
import top.jtning.rpc.enumeration.RpcError;
import top.jtning.rpc.exception.RpcException;
import top.jtning.rpc.serializer.CommonSerializer;
import top.jtning.rpc.util.RpcMessageChecker;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

public class NettyClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private String host;
    private int port;
    private CommonSerializer serializer;
    private static final Bootstrap bootstrap;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true);
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if (serializer == null) {
            logger.error("serializer not set");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
//        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
//
//            @Override
//            protected void initChannel(SocketChannel ch) throws Exception {
//                ChannelPipeline pipeline = ch.pipeline();
//                pipeline.addLast(new CommonDecoder())
//                        .addLast(new CommonEncoder(serializer))
//                        .addLast(new NettyClientHandler());
//            }
//        });
        AtomicReference<Object> result = new AtomicReference<>(null);
        try {
//            ChannelFuture future = bootstrap.connect(host, port).sync();
//            logger.info("client connected to server {}:{}", host, port);
//            Channel channel = future.channel();
//            if (channel != null) {
            Channel channel = ChannelProvider.get(new InetSocketAddress(host, port), serializer);
            if (channel.isActive()){
            channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if (future1.isSuccess()) {
                        logger.info("server send message: {}", rpcRequest.toString());
                    } else {
                        logger.error("server send message fail in channel: ", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse" + rpcRequest.getRequestId());
                RpcResponse rpcResponse = channel.attr(key).get();
                RpcMessageChecker.check(rpcRequest, rpcResponse);
//                return rpcResponse.getData();
                result.set(rpcResponse.getData());
            }else
                System.exit(0);
        } catch (InterruptedException e) {
            logger.error("server send message fail: ", e);
        }
        return result.get();
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}

