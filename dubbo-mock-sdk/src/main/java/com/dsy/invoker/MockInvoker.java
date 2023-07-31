package com.dsy.invoker;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.ClusterInvoker;
import org.apache.dubbo.rpc.cluster.Directory;

/**
 * @Author: dongshuaiyin
 * @Description:
 * @Date: 2023/4/21 16:02
 * @Modified by:
 */
public class MockInvoker<T> implements ClusterInvoker<T> {

    private final Directory<T> directory;

    private final Invoker<T> invoker;

    public MockInvoker(Directory<T> directory, Invoker<T> invoker) {
        this.directory = directory;
        this.invoker = invoker;
    }

    @Override
    public Class<T> getInterface() {
        return this.directory.getInterface();
    }

    @Override
    public Result invoke(Invocation invocation) throws RpcException {
        String interfaceName = invoker.getInterface().getCanonicalName();
        String methodName = invocation.getMethodName();
        return MockHandler.invoke(invoker, invocation, interfaceName, methodName);
    }

    @Override
    public URL getUrl() {
        return directory.getConsumerUrl();
    }

    public URL getRegistryUrl() {
        return directory.getUrl();
    }

    @Override
    public Directory<T> getDirectory() {
        return directory;
    }

    @Override
    public boolean isDestroyed() {
        return directory.isDestroyed();
    }

    @Override
    public boolean isAvailable() {
        return directory.isAvailable();
    }

    @Override
    public void destroy() {
        this.invoker.destroy();
    }

}
