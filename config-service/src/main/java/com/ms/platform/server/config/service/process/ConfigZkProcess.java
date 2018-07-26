package com.ms.platform.server.config.service.process;

import com.ms.common.bo.prop.PlatformProp;
import com.ms.common.utils.JSONUtils;
import com.ms.platform.configcenter.core.ZooKeeperFactory;
import com.ms.platform.server.config.common.constants.Constants;
import com.ms.platform.server.config.model.ServerConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joey on 2017/7/12 0012.
 */
@Component
public class ConfigZkProcess {

    private static final Logger logger = LoggerFactory.getLogger(ConfigZkProcess.class);

    private String authAdminKey = "ms.platform.config.authadmin";

    private ACL aclAdmin = null;

    @PostConstruct
    public void init(){
        try {
            Id idAdmin = new Id("digest", DigestAuthenticationProvider.generateDigest(PlatformProp.getProperty(authAdminKey)));
            aclAdmin = new ACL(ZooDefs.Perms.ALL, idAdmin);
        } catch (NoSuchAlgorithmException e) {
            logger.error("{}",e);
        }
    }

    public void syncConfig(String appId,String appNamespaceName,String password,List<ServerConfig> serverConfigList) throws Exception{
        if(null == serverConfigList || serverConfigList.isEmpty()){
            logger.warn("serverConfigList is null or empty");
            return;
        }

        List<ACL> acls = new ArrayList<>(2);
        Id idUser = new Id("digest", DigestAuthenticationProvider.generateDigest("user:"+password));
        ACL aclUser = new ACL(ZooDefs.Perms.READ, idUser);
        acls.add(aclAdmin);
        acls.add(aclUser);

        String jsonData = JSONUtils.toJSON(serverConfigList);
        String configAppPath = "/"+Constants.CONFIG_ZK_NAMESPACE+"/" + appId +"/" + appNamespaceName;

        CuratorFramework client = ZooKeeperFactory.getCuratorFramework();
        ZooKeeper zooKeeper = client.getZookeeperClient().getZooKeeper();
        if(!exists(client,configAppPath)) {
            client.create().creatingParentsIfNeeded().forPath(configAppPath, jsonData.getBytes("utf-8"));
        }else{
            zooKeeper.addAuthInfo("digest", PlatformProp.getProperty(authAdminKey).getBytes());
            zooKeeper.setData(configAppPath,jsonData.getBytes("utf-8"),-1);
        }
        client.setACL().withACL(acls).forPath(configAppPath);
    }

    private boolean exists(CuratorFramework client, String path) throws Exception {
        Stat stat = client.checkExists().forPath(path);
        return!(stat == null);
    }
}
