package ms.platform.server;

import com.ms.common.utils.Md5Utils;

/**
 * Created by Joey on 2017/8/14 0014.
 */
public class TokenTest {

    public static void main(String[] args) {
//        TokenProvider tokenProvider = new TokenProvider();
//        tokenProvider.expiration = 10;
//        tokenProvider.secret = "sadfa;jj1231231sdf;jsk5757";
//
//        List<String> roles = new ArrayList<>();
//        roles.add(Permissions.ROLE_USER);
//        roles.add(Permissions.ROLE_ADMIN);
//
//        Map<String, Object> claims = new HashMap<>();
//        claims.put(Constants.CLAIM_KEY_USERID, 1+"");
//        claims.put(Constants.CLAIM_KEY_ROLE,roles);
//        claims.put(Constants.CLAIM_KEY_CREATED, new Date());
//        String accessToken =  tokenProvider.generateToken(claims);
//        System.out.println(accessToken);
//
//        String userId = tokenProvider.getTokenUserId(accessToken);
//        System.out.println(userId);
//
//        Claims claims1 = tokenProvider.parseJWT(accessToken);
//        List<String> strRoles = claims1.get(Constants.CLAIM_KEY_ROLE,List.class);
//        System.out.println(strRoles);

        String p = "123456";
        String pw = Md5Utils.getMD5(p);
        System.out.println(pw);


    }

}
