package top.jtning.rpc.transport;

import top.jtning.rpc.serializer.CommonSerializer;

public interface RpcServer {
    void start(int port);

    void setSerializer(CommonSerializer commonSerializer);
}
