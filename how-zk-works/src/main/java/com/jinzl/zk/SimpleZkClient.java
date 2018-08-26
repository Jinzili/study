package com.jinzl.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class SimpleZkClient {

    private static final String CONNECT_STRING = "bigdata1:2181,bigdata2:2181,bigdata3:2181";

    private static final Integer SESSION_TIMEOUT = 2000;

    private ZooKeeper zkClient = null;

    /**
     * 初始化client
     */
    @Before
    public void init() throws IOException {
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

    /**
     * 数据的增删改查
     */
    // 创建数据
    @Test
    public void create() throws KeeperException, InterruptedException {
        // 路径 数据 权限 类型
        String create = zkClient.create("/idea",
                "hello world".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println("create: " + create);
    }

    // 子节点是否存在
    @Test
    public void exists() throws KeeperException, InterruptedException {
        Stat stat = zkClient.exists("/idea", false);
        System.out.println(stat == null ? "not exists" : "exists");
    }

    // 获取子节点数据
    @Test
    public void getChildren() throws KeeperException, InterruptedException {
        // 路径 是否监听数据变化
        List<String> childs = zkClient.getChildren("/", true);
        childs.forEach(System.out::println);
    }

    // 获取节点数据
    @Test
    public void get() throws KeeperException, InterruptedException {
        byte[] data = zkClient.getData("/idea", false, null);
        System.out.println(new String(data));
    }

    // 删除节点数据
    @Test
    public void delete() throws KeeperException, InterruptedException {
        // 参数2, 指定要删除的版本, -1表示删除所有版本
        zkClient.delete("/idea", -1);
    }

    // 设置节点数据
    @Test
    public void set() throws KeeperException, InterruptedException {
        zkClient.setData("/idea", "hello world 2".getBytes(), -1);
    }

}
