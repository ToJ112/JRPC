package top.jtning.rpc;

import top.jtning.rpc.entity.RpcRequest;

public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}
