package top.jtning.rpc.registry;

import java.net.InetSocketAddress;

public class NacosServiceRegistry implements ServiceRegistry{
    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {

    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        return null;
    }
}
