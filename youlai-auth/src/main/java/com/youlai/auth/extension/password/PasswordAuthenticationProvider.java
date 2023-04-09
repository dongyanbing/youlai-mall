package com.youlai.auth.extension.password;

import com.youlai.auth.userdetails.user.SysUserDetailsServiceImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 用户名密码认证授权提供者
 * @author zc
 * @date 2023-04-09 14:33
 */
@Component
public class PasswordAuthenticationProvider extends DaoAuthenticationProvider {



    public PasswordAuthenticationProvider(SysUserDetailsServiceImpl userDetailsService,PasswordEncoder passwordEncoder) {
        super.setUserDetailsService(userDetailsService);
        super.setPasswordEncoder(passwordEncoder);
        super.setHideUserNotFoundExceptions(false); // 是否隐藏用户不存在异常，默认:true-隐藏；false-抛出异常；
    }


    /**
     * 认证
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return super.authenticate(authentication);
    }
}
