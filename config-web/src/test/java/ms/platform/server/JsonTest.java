package ms.platform.server;

import com.ms.common.utils.JSONUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joey on 2017/9/13 0013.
 */
public class JsonTest {

    public static void main(String[] args) throws Exception {

        List<String> cis = new ArrayList<String>();
        cis.add("111");
        cis.add("222");
        System.out.println(JSONUtils.toJSON(cis));
    }

}
