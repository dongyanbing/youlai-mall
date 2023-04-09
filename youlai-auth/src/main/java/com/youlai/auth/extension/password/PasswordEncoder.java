package com.youlai.auth.extension.password;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 密码编码器
 * 委托方式，根据密码的前缀选择对应的encoder，例如：{bcypt}前缀->标识BCYPT算法加密；{noop}->标识不使用任何加密即明文的方式
 * 密码判读 DaoAuthenticationProvider#additionalAuthenticationChecks
 * @author zc
 * @date 2023-04-09 14:34
 */
@Component
@Slf4j
public class PasswordEncoder extends DelegatingPasswordEncoder {


    private final RedisTemplate redisTemplate;
   private static String encodingId = "bcrypt";
    private static Map<String, org.springframework.security.crypto.password.PasswordEncoder> encoders = new HashMap<String, org.springframework.security.crypto.password.PasswordEncoder>(){{
        this.put(encodingId, new BCryptPasswordEncoder());
        this.put("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
        this.put("MD4", new org.springframework.security.crypto.password.Md4PasswordEncoder());
        this.put("MD5", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5"));
        this.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
        this.put("pbkdf2", new Pbkdf2PasswordEncoder());
        this.put("scrypt", new SCryptPasswordEncoder());
        this.put("SHA-1", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1"));
        this.put("SHA-256",
                new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256"));
        this.put("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder());
        this.put("argon2", new Argon2PasswordEncoder());
    }};

    public PasswordEncoder(RedisTemplate redisTemplate) {
        super( encodingId,encoders);
        this.redisTemplate = redisTemplate;
    }


    @Override
    public boolean matches(CharSequence rawPassword, String prefixEncodedPassword) {
        String key = "com.youlai.auth.extension.password.PasswordEncoder.matches:"+rawPassword+prefixEncodedPassword;
        Object o = redisTemplate.opsForValue().get(key);
        if(o!=null){
            return (boolean)o;
        }
        boolean matches = super.matches(rawPassword, prefixEncodedPassword);
        redisTemplate.opsForValue().set(key,matches);
        return matches;
    }
}
