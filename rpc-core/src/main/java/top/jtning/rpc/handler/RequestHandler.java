package top.jtning.rpc.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jtning.rpc.entity.RpcRequest;
import top.jtning.rpc.entity.RpcResponse;
import top.jtning.rpc.enumeration.ResponseCode;
import top.jtning.rpc.provider.ServiceProvider;
import top.jtning.rpc.provider.ServiceProviderImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final ServiceProvider serviceProvider;
    static {
        serviceProvider = new ServiceProviderImpl();
    }
    public Object handle(RpcRequest rpcRequest) {
        Object result = null;
        Object service = serviceProvider.getServiceProvider(rpcRequest.getInterfaceName());
        try {
            result = invokeTargetMethod(rpcRequest, service);
            logger.info("Service: {} Successfully invoked method: {}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (InvocationTargetException | IllegalAccessException e) {
            logger.error("An error occurred while calling or sending:" + e);
        }
        return result;
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws InvocationTargetException, IllegalAccessException {
        Method method;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND, rpcRequest.getRequestId());
        }
        return method.invoke(service, rpcRequest.getParameters());
    }
}
