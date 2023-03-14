package top.jtning.rpc.netty.server;

import io.netty.channel.*;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jtning.rpc.RequestHandler;
import top.jtning.rpc.entity.RpcRequest;
import top.jtning.rpc.entity.RpcResponse;
import top.jtning.rpc.registry.DefaultServiceRegistry;
import top.jtning.rpc.registry.ServiceRegistry;

public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private static RequestHandler requestHandler;
    private static ServiceRegistry serviceRegistry;

    static {
        requestHandler = new RequestHandler();
        serviceRegistry = new DefaultServiceRegistry();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        try {
            logger.info("server receive request: {}", msg);
            String interfaceName = msg.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            Object result = requestHandler.handle(msg, service);
            ChannelFuture future = ctx.writeAndFlush(RpcResponse.success(result));
            future.addListener(ChannelFutureListener.CLOSE);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Invocate service fail:");
        cause.printStackTrace();
        ctx.close();
    }
}
