package top.jtning.rpc.registry;

import java.net.InetSocketAddress;

public interface ServiceDiscovery {
    //根据服务名称从注册中心获取到一个服务提供者的地址
    InetSocketAddress lookupService(String serviceName);

}
