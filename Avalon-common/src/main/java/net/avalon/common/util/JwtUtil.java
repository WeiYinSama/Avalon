package net.avalon.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * JwtUtil
 */
public class JwtUtil {

    //初始化密钥
    private static final String SECRET = "Ciallo~";

    //token是由谁发行的
    private static final String ISSUER = "Lacia";

    //token的主题
    private static final String SUBJECT = "身份校验";

    //token的使用对象
    private static final String AUDIENCE = "Avalon";

    //请求头的key
    public static final String LOGIN_TOKEN_KEY = "authorization";

    //初始化算法
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET);
    //初始化验证器
    private static final JWTVerifier verifier = JWT.require(algorithm).withIssuer("Lacia").build();

    /**
     * 创建 Token
     * @param user 用户
     * @param expireTime 过期时间
     * @return
     */
    public static String createToken(User user, int expireTime) {
        try {
            //token创建时间
            Date now = new Date();
            //token过期时间
            Date expireDate = getAfterDate(now, 0, 0, 0, 0, 0, expireTime);
            //生成token
            String token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(SUBJECT)
                    .withAudience(AUDIENCE)
                    .withIssuedAt(now)
                    .withExpiresAt(expireDate)
                    .withClaim("uid", user.getId())
                    .withClaim("name", user.getName())
                    .sign(algorithm);
            return token;

        } catch (JWTCreationException exception) {
            // System.out.println("token创建失败");
            return null;
        }
    }

    /**
     * 验证并获得 TokenInfo
     * @param token 令牌
     * @return 令牌信息，保存着用户相关内容
     */
    public static TokenInfo verifyToken(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }
        try {
            DecodedJWT jwt = verifier.verify(token);
            Long uid = jwt.getClaim("uid").asLong();
            String name = jwt.getClaim("name").asString();

            Date expiresAt = jwt.getExpiresAt();
            return new TokenInfo(new User(uid,name), expiresAt);

        } catch (JWTVerificationException exception) {
            // System.out.println("token验证失败");
            return null;
        }
    }

    private static Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second) {
        if (date == null) {
            date = new Date();
        }

        Calendar cal = new GregorianCalendar();

        cal.setTime(date);
        if (year != 0) {
            cal.add(Calendar.YEAR, year);
        }
        if (month != 0) {
            cal.add(Calendar.MONTH, month);
        }
        if (day != 0) {
            cal.add(Calendar.DATE, day);
        }
        if (hour != 0) {
            cal.add(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute != 0) {
            cal.add(Calendar.MINUTE, minute);
        }
        if (second != 0) {
            cal.add(Calendar.SECOND, second);
        }
        return cal.getTime();
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public static class TokenInfo {
        private User user;
        private Date expiresAt;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User{
        private Long id;
        private String name;
    }
}