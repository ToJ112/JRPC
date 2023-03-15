package top.jtning.rpc.registry;

import java.net.InetSocketAddress;

public interface ServiceRegistry {
    //将服务的名称和地址注册进服务注册中心
    void register(String serviceName, InetSocketAddress inetSocketAddress);
}
