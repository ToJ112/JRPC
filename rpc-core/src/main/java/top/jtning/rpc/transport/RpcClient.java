package top.jtning.rpc.transport;

import top.jtning.rpc.entity.RpcRequest;
import top.jtning.rpc.serializer.CommonSerializer;

public interface RpcClient {
    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;
    Object sendRequest(RpcRequest rpcRequest);
}
