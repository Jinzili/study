package com.jinzl.zk.zkdist;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class DistributedServer {

    private static final String CONNECT_STRING = "bigdata1:2181,bigdata2:2181,bigdata3:2181";

    private static final Integer SESSION_TIMEOUT = 2000;

    private static final String PARENT_NODE = "/servers";

    private ZooKeeper zkClient = null;

    // 获取zkClient
    private void init() throws IOException {
        zkClient = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, (event) -> {
            // 收到时间通知后的回调函数 是我们自己的事件处理逻辑
            // 初始化时会收到一次
            System.out.println(event.getType() + "---" + event.getPath());
            try {
                // 重新执行监听
                zkClient.getChildren("/", true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // 注册服务器信息 创建一个临时的带顺序的节点
    private void register(String hostname) throws KeeperException, InterruptedException {
        String createPath = zkClient.create(PARENT_NODE + "/server",
                hostname.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostname + " is online. PATH is " + createPath);
    }

    // 业务功能
    private void handleBussiness(String hostname) throws InterruptedException {
        System.out.println(hostname + " start working");
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        // 获取zk连接
        DistributedServer server = new DistributedServer();
        server.init();
        // 利用zk连接注册服务器信息
        server.register(args[0]);
        // 启动业务功能
        server.handleBussiness(args[0]);
    }

}
