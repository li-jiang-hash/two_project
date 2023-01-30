package com.aaa.sso.config;

import com.aaa.sso.service.MyCustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;

@Configuration
@EnableAuthorizationServer
public class oauth2Config extends AuthorizationServerConfigurerAdapter {
    private static final Integer TOKEN_ACTIVE_TIME=2*60*60;
    private static final Integer REFESH_TOKEN_ACTIVE_TIME=24*60*60;


    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    @Resource
    private MyCustomUserDetailsService userDetailsService;
    @Resource
    private AuthenticationManager authenticationManager;


    /**
     * 配置的第三方的客户端的信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //
        clients.inMemory().withClient("client_id")// 第三方的客户端的id  client_id
                .secret(passwordEncoder.encode("secret"))//  客户端的密钥 client_secret
                /**
                 * 支持哪些认证模式
                 * authorization_code 授权码模式
                 * password 密码模式final
                 * implicit 简单模式
                 * client_credentials 客户端模式
                 * refresh_token 这个不是一种模式 是支持刷新令牌的意思
                 */
                .authorizedGrantTypes("password","refresh_token")// 字符串一定不能出错！！！！！
                .accessTokenValiditySeconds(TOKEN_ACTIVE_TIME)
                .refreshTokenValiditySeconds(REFESH_TOKEN_ACTIVE_TIME)
                .scopes("all");


    }

    /**
     * 安全配置
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")// 生成token的路径 oauth/token_key
                .checkTokenAccess("permitAll()")// oauth/check_token
                .allowFormAuthenticationForClients() // 路径 申请token（令牌）
        ;
    }

    @Bean
    public JwtTokenStore getTokenStore()    {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        // 设置签名
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("zhangxuhui");
        return jwtAccessTokenConverter;
    }

    /**
     * 凭证的信息
     * @param endpoints
     * @throws Exception
     */

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(getTokenStore())// 创建令牌的时候使用的类
                .accessTokenConverter(jwtAccessTokenConverter())// 解析令牌的时候使用的类
                .userDetailsService(userDetailsService)// 认证的时候使用的类是什么
                .authenticationManager(authenticationManager);
    }

}
