package xyz.wanghongtao.rebac.util;



import lombok.extern.slf4j.Slf4j;
import xyz.wanghongtao.rebac.exception.CustomException;

import java.util.Date;
import java.util.Objects;

@Slf4j
public class JwtUtil {

    private static String secret = "kktuyhocrjhlndcv";

    private static Long expiration = 86400L;

    /**
     * 创建token
     *
     * @param username 用户名
     * @return
     */
    public static String generateToken(String username,Integer id) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS384, secret)
                .claim("username", username)
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }


    /**
     * 是否已过期
     *
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        return Objects.requireNonNull(getTokenBody(token)).getExpiration().before(new Date());
    }


    /**
     * 获取token中的信息
     * 同时解析判断是否为正确的JWT
     *
     * @param token
     * @return
     */
    public static Claims getTokenBody(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("解析异常:{}", e.getMessage());
            throw new CustomException(ResultEnum.TOKEN_ERROR);
        }
    }

    public static String getUsernameByToken(String token) {
        try {
            return (String) Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .get("username");
        } catch (Exception e) {
            log.error("解析异常:{}", e.getMessage());
            throw new CustomException(ResultEnum.TOKEN_ERROR);
        }
    }

}
