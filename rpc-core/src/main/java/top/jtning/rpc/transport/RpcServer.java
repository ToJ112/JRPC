package top.jtning.rpc.transport;

import top.jtning.rpc.serializer.CommonSerializer;

public interface RpcServer {
    void start();

    void setSerializer(CommonSerializer commonSerializer);

    <T> void publishService(Object service, Class<T> serviceClass);
}
