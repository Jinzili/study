package com.jinzl.zk.zkdist;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistributedClient {

    private static final String CONNECT_STRING = "bigdata1:2181,bigdata2:2181,bigdata3:2181";

    private static final Integer SESSION_TIMEOUT = 2000;

    private static final String PARENT_NODE = "/servers";

    private ZooKeeper zkClient = null;
    private volatile List<String> servers = null;
    // 获取zkClient
    private void init() throws IOException {
        zkClient = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, (event) -> {
            // 收到时间通知后的回调函数 是我们自己的事件处理逻辑
            // 初始化时会收到一次
            System.out.println(event.getType() + "---" + event.getPath());
            try {
                // 获取服务器列表, 并且重新注册监听
                getServers();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void getServers() throws KeeperException, InterruptedException {
        List<String> childrens = zkClient.getChildren(PARENT_NODE, true);
        List<String> servers = new ArrayList<>();
        childrens.forEach(_child -> {
            try {
                byte[] data = zkClient.getData(PARENT_NODE + "/" + _child, false, null);
                servers.add(new String(data));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.servers = servers;
        servers.forEach(System.out::println);
    }

    // 业务功能
    private void handleBussiness() throws InterruptedException {
        System.out.println("client start working");
        // zookeeper的线程为守护线程, 主线程退出即退出, 所以此处sleep
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        // 获取zk连接
        DistributedClient client = new DistributedClient();
        client.init();
        // 获取并监听servers节点, 获取服务器列表
        client.getServers();
        // 业务功能
        client.handleBussiness();
    }

}
