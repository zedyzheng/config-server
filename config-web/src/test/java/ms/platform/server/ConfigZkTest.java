//package ms.platform.server;
//
//import com.ms.common.bo.constants.ConfigConstants;
//import com.ms.common.bo.prop.PlatformProp;
//import com.ms.common.utils.JSONUtils;
//import com.ms.platform.server.config.common.constants.Constants;
//import com.ms.platform.server.config.model.ServerConfig;
//import org.apache.curator.RetryPolicy;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.apache.zookeeper.ZooKeeper;
//import org.apache.zookeeper.data.Stat;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Joey on 2017/8/14 0014.
// */
//public class ConfigZkTest {
//
//    private static Logger logger = LoggerFactory.getLogger(ConfigZkTest.class);
//
//    public static List<ServerConfig> getServerConfigsFromData(byte[] data) throws Exception{
//        String jsonData = new String(data,"UTF-8");
//        logger.info("获取的配置文件信息 : {}",jsonData);
//        return JSONUtils.toList(jsonData, ServerConfig.class);
//    }
//
//    public static void main(String[] args) throws Exception{
//        PlatformProp.setProperty(ConfigConstants.CONFIG_APP_SERVER,"192.168.2.106:2181,192.168.2.107:2181,192.168.2.108:2181");
//
//        String appId = "ms.platform.server.user";
//        String appNamespaceName = "dev";
//        String configAppPath = "/"+ Constants.CONFIG_ZK_NAMESPACE+"/" + appId +"/" + appNamespaceName;
//
//        List<ServerConfig> serverConfigList = new ArrayList<>();
//        ServerConfig serverConfig = new ServerConfig();
//        serverConfig.setAppId(appId);
//        serverConfig.setAppNamespaceId(1L);
//        serverConfig.setId(1L);
//        serverConfig.setItemKey("redis.host");
//        serverConfig.setItemValue("192.168.2.228");
//        serverConfigList.add(serverConfig);
//
//        //String password = "123456";
//        //password = Md5Utils.getMD5(password);
//        //String auth = "user:"+password;
//
//        CuratorFramework client = createWithOptions();
//        ZooKeeper zooKeeper = client.getZookeeperClient().getZooKeeper();
//
//        while (true){
//            String jsonData = "\"{\\\"testfsfjsfafa\\\":\\\"dsfffffffffffffffffffffffffffffffffffffffff\" +\n" +
//                    "                    \"ffffffffffffffffff345353535khjkhk4646sd4fsdfsdddddddddddddddddddd\" +\n" +
//                    "                    \"dddddddddddddddddddddddddddddddddddddddddddddd\" +\n" +
//                    "                    \"ddddddddddsffffffffffffffffffffffffffffff24566cvbncn78sdgf79dasg7dagfdddddddddd\" +\n" +
//                    "                    \"dddddddddddddddddddddddddddddddddddddddddddddfddddd\\\"}\"";
//
//            if(!exists(client,configAppPath)) {
//                client.create().creatingParentsIfNeeded().forPath(configAppPath, jsonData.getBytes("utf-8"));
//            }else{
//                //zooKeeper.addAuthInfo("digest", PlatformProp.getProperty(authAdminKey).getBytes());
//                zooKeeper.setData(configAppPath,jsonData.getBytes("utf-8"),-1);
//            }
//
////        ConfigZkProcess configZkProcess = new ConfigZkProcess();
////        configZkProcess.setZkAuthAdmin("admin:admin@123!@#");
////        configZkProcess.setZdAuthUser("user:micro@123");
////        configZkProcess.syncConfig(appId,appNamespaceName,serverConfigList);
//            for (int i = 0; i < 1; i++) {
//                //zooKeeper.addAuthInfo("digest",auth.getBytes());
//                byte[] datas = zooKeeper.getData(configAppPath,false,null);
//                String result = new String(datas,"UTF-8");
//                System.out.println(result);
//                //getServerConfigsFromData(datas);
//            }
//        }
//    }
//
//
//    //通过自定义参数创建
//    private static CuratorFramework createWithOptions(){
//        String zkServer = PlatformProp.getProperty(ConfigConstants.CONFIG_APP_SERVER);
//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 3);
//
//        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
//                .connectString(zkServer)
//                .retryPolicy(retryPolicy)
//                .connectionTimeoutMs(2000)
//                .sessionTimeoutMs(10000)
//                .build();
//
//        curatorFramework.start();
//        return curatorFramework;
//    }
//
//    private static boolean exists(CuratorFramework client, String path) throws Exception {
//        Stat stat = client.checkExists().forPath(path);
//        return!(stat == null);
//    }
//}
