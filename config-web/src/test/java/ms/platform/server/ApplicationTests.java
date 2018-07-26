package ms.platform.server;

import com.ms.platform.server.config.dal.entity.UserEntity;
import com.ms.platform.server.config.dal.repository.UserDao;
import com.ms.platform.server.config.web.ConfigConsoleApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Joey on 2017/8/10 0010.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(ConfigConsoleApplication.class)
//@WebAppConfiguration
//@IntegrationTest("server.port:0")
public class ApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void test() throws Exception {
        UserEntity userEntity = userDao.findByUserName("123");
        //List<UserRole> userRole = userRoleRepository.findByuid(1);

//        List<String> list = userRoleRepository.findRoleName(1);
//        System.out.println(list.get(0));
    }

}
