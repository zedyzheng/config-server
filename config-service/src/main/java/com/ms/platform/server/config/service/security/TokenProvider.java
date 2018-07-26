package com.ms.platform.server.config.service.security;

import com.ms.platform.server.config.common.constants.Permissions;
import com.ms.platform.server.config.common.constants.Constants;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Joey on 2017/8/11 0011.
 */
@Component
public class TokenProvider {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expirationInSeconds}")
    private Integer expiration;

//    @Autowired
//    private Environment environment;

    public String createToken(String userId,List<String> roles,String accessAppId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.CLAIM_KEY_USERID, userId);
        claims.put(Constants.CLAIM_KEY_ROLE,roles);
        if(null!=accessAppId){
            claims.put(Constants.CLAIM_KEY_ACCESS_APPID,accessAppId);
        }
        claims.put(Constants.CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    public String createToken(String userId) {
        List<String> roles = new ArrayList<>();
        roles.add(Permissions.ROLE_USER);
        return createToken(userId, roles,null);
    }

    public Claims parseJWT(String authToken){
        try
        {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();
            return claims;
        }
        catch(SignatureException ex){
            return null;
        }
    }

    private String generateToken(Map<String, Object> claims) {
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS384, secret);
        //添加Token过期时间
        if (expiration >= 0) {
            builder.setExpiration(generateExpirationDate());
        }
        //生成JWT
        return builder.compact();
    }

    public String refreshToken(String authToken) {
        String refreshedToken;
        try {
            final Claims claims = parseJWT(authToken);
            claims.put(Constants.CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            return false;
        }
    }

    public String getTokenUserId(String authToken) {
        Claims claims = this.parseJWT(authToken);
        if (null!=claims) {
            return claims.get(Constants.CLAIM_KEY_USERID,String.class);
        }
        return null;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

//    public Authentication getAuthentication(String token) {
//        Claims claims = Jwts.parser()
//            .setSigningKey(secretKey)
//            .parseClaimsJws(token)
//            .getBody();
//
//        Collection<? extends GrantedAuthority> authorities =
//            Arrays.asList(claims.get(AUTHORITIES_KEY).toString().split(",")).stream()
//                .map(authority -> new SimpleGrantedAuthority(authority))
//                .collect(Collectors.toList());
//
//        User principal = new User(claims.getSubject(), "",
//            authorities);
//
//        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
//    }

}
