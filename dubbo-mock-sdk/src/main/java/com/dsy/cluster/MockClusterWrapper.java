package com.dsy.cluster;

import com.dsy.config.MockConfig;
import com.dsy.invoker.MockInvoker;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.Cluster;
import org.apache.dubbo.rpc.cluster.Directory;

/**
 * @Author: dongshuaiyin
 * @Description:
 * @Date: 2023/4/21 15:57
 * @Modified by:
 */
public class MockClusterWrapper implements Cluster {

    private Cluster cluster;

    public MockClusterWrapper(Cluster cluster) {
        this.cluster = cluster;
    }

    @Override
    public <T> Invoker<T> join(Directory<T> directory) throws RpcException {
        //直接将mock添加到链路中
        return new MockInvoker<>(directory, this.cluster.join(directory));
    }
}
