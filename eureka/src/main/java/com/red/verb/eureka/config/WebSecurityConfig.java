package com.red.verb.eureka.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/19     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-19 13:06
 * @since 1.0.0
 */

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * ignoringAntMatchers(String... antPatterns)：表示对某些请求，如果符合匹配的规则，则忽略对它们的 CSRF 防护
         * /eureka/**：首斜杠表示应用上下文路径，也就是说对所有符合 http://ip:port/context-paht/eureka/** 的请求全部忽略对它们的 CSRF 防护
         * 所以只要 eureka.client.serviceUrl.defaultZone 的值符合此规则，就不会被 CSRF 防护了
         */
        http.csrf().ignoringAntMatchers("/eureka/**");
        /**
         * 还有一个一劳永逸的方式是直接禁止  CSRF 防护:
         * http.csrf().disable();//完全禁止 CSRF 防护
         */
        super.configure(http);
    }

}
