package top.jtning.rpc.transport;

import top.jtning.rpc.entity.RpcRequest;
import top.jtning.rpc.serializer.CommonSerializer;

public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);

    void setSerializer(CommonSerializer commonSerializer);
}
