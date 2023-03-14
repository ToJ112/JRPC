package top.jtning.rpc.netty.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jtning.rpc.entity.RpcResponse;

public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
    private static final Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse rpcResponse) throws Exception {
        try {
            logger.info("client receive message: {}",rpcResponse);
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
            ctx.channel().attr(key).set(rpcResponse);
            ctx.close();
        }finally {
            ReferenceCountUtil.release(rpcResponse);
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("fail when invocate service in client:");
        cause.printStackTrace();
        ctx.close();
    }
}
