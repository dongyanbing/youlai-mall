package com.youlai.auth.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.youlai.auth.extension.mobile.SmsCodeAuthenticationProvider;
import com.youlai.auth.extension.password.PasswordAuthenticationProvider;
import com.youlai.auth.extension.wechat.WechatAuthenticationProvider;
import com.youlai.mall.ums.api.MemberFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;
import java.util.List;

@ConfigurationProperties(prefix = "security")
@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordAuthenticationProvider passwordAuthenticationProvider;
    private final UserDetailsService memberUserDetailsService;
    private final WxMaService wxMaService;
    private final MemberFeignClient memberFeignClient;
    private final StringRedisTemplate redisTemplate;

    @Setter
    private List<String> ignoreUrls;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (CollectionUtil.isEmpty(ignoreUrls)) {
            ignoreUrls = Arrays.asList("/webjars/**", "/doc.html", "/swagger-resources/**", "/v2/api-docs");
        }

        log.info("whitelist path:{}", JSONUtil.toJsonStr(ignoreUrls));

        http
                .authorizeRequests()
                .antMatchers(Convert.toStrArray(ignoreUrls)).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    /**
     * 认证管理对象
     *
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(wechatAuthenticationProvider()).
                authenticationProvider(passwordAuthenticationProvider).
                authenticationProvider(smsCodeAuthenticationProvider());
    }

    /**
     * 手机验证码认证授权提供者
     *
     * @return
     */
    @Bean
    public SmsCodeAuthenticationProvider smsCodeAuthenticationProvider() {
        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
        provider.setUserDetailsService(memberUserDetailsService);
        provider.setRedisTemplate(redisTemplate);
        return provider;
    }

    /**
     * 微信认证授权提供者
     *
     * @return
     */
    @Bean
    public WechatAuthenticationProvider wechatAuthenticationProvider() {
        WechatAuthenticationProvider provider = new WechatAuthenticationProvider();
        provider.setUserDetailsService(memberUserDetailsService);
        provider.setWxMaService(wxMaService);
        provider.setMemberFeignClient(memberFeignClient);
        return provider;
    }


}
