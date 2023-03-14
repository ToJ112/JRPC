package top.jtning.rpc;

import top.jtning.rpc.serializer.CommonSerializer;

public interface RpcServer {
    void start(int port);

    void setSerializer(CommonSerializer commonSerializer);
}
