package com.aaa.sso.config;


import com.aaa.sso.provider.MultipleLoginAuthenticationProvider;
import com.aaa.sso.provider.MyDaoAuthenricationProvider;
import com.aaa.sso.service.MyCustomUserDetailsService;
import com.aaa.util.Result;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyCustomUserDetailsService userDetailsService;

    @Resource
    private MultipleLoginAuthenticationSecurityConfig multipleLoginAuthenticationSecurityConfig;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder)
//                .withUser("syf")
//                .password(passwordEncoder.encode("123456"))
//                .roles("admin");
        //auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(daoAuthenticationProvider());
        auth.authenticationProvider(customAuthenticationProvider2());
    }
    // 获取token的时候使用的
    private AuthenticationProvider daoAuthenticationProvider() {
        MyDaoAuthenricationProvider daoAuthenticationProvider = new MyDaoAuthenricationProvider();
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    // 认证的时候使用的
    public AuthenticationProvider customAuthenticationProvider2() {
        MultipleLoginAuthenticationProvider customAuthenticationProvider2 = new MultipleLoginAuthenticationProvider();
        //System.out.println("设置的service路径**********。。。。。");
        customAuthenticationProvider2.setUserDetailsService(userDetailsService);
        customAuthenticationProvider2.setPasswordEncoder(getPasswordEncoder());
        return customAuthenticationProvider2;
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin()
//                //.loginPage("/index1.html")
//                //.usernameParameter("username").passwordParameter("password")//这一行默认是username和password，可以省略这一行
//                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/quit.jsp").permitAll()
//                .and()
//                .logout().logoutUrl("/logout").logoutSuccessUrl("/")
//                .and().exceptionHandling().accessDeniedPage(("/403.html"))
//                .and()
//                .authorizeRequests().antMatchers("/","/my/info").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .csrf().disable();
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.apply(multipleLoginAuthenticationSecurityConfig);
        http.formLogin()
//                .loginProcessingUrl("/login")
//                .successHandler(getSuccessHadler()).permitAll()
//                .failureHandler(getFileureHandler())
                .and()
                .logout().logoutUrl("/logout").logoutSuccessHandler(getLogOut()).permitAll()
                .and().exceptionHandling().accessDeniedHandler(getAccessDefined())
                .and()
                .authorizeRequests().antMatchers("/","/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .cors()//允许跨域
                .and()
                .csrf().disable();
    }

    /**
     * 权限不足
     * @return
     */
    private AccessDeniedHandler getAccessDefined() {
        return ((request, response, accessDeniedException) -> {
            Result result = new Result(403,"没有权限",null);
            printJsonData(response,result);
        });
    }

    public void printJsonData(HttpServletResponse response,Result result) throws IOException {
            response.setContentType("application/json;charset=utf8");
            PrintWriter writer = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(result);

            writer.print(s);
            writer.flush();
            writer.close();

    }
    /**
     * 退出之后的handler
     * @return
     */
    private LogoutSuccessHandler getLogOut() {
        return ((request, response, authentication) -> {
            Result result = new Result(200, "退出成功", null);
            printJsonData(response,result);
        });
    }

//    /**
//     * 认证成功
//     * @return
//     */
//    public AuthenticationSuccessHandler getSuccessHadler(){
//        return (request, response, authentication) -> {
//
//            //http://localhost:7200/oauth/token?username=ls&password=123456&client_id=client_id&client_secret=secret&grant_type=password
//            String url="http://localhost:7200/oauth/token";
//            HashMap<String, String> map = new HashMap<>();
//            map.put("client_id","client_id");
//            map.put("client_secret","secret");
//            map.put("grant_type","password");
//            String username = request.getParameter("username");
//            String password = request.getParameter("password");
//            map.put("username",username);
//            map.put("password",password);
//
//            Response res = OkHttpClientUtil.getInstance().postData(url, map);
//            System.out.println("res = " + res);
//            String s = Objects.requireNonNull(res.body().string());
//            System.out.println("JSON"+s);
//            Map<String, Object> map1 = new HashMap<>();
//            try {
//                map1=json2map(s);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            String o = map1.get("access_token").toString();
//            System.out.println("access_token = " + o);
//            String type = map1.get("token_type").toString();
//            String token=type+" "+o;
//            System.out.println("token = " + token);
//            Result result = new Result(token);
//           printJsonData(response,result);
//        };
//    }

//    /**
//     * 认证失败
//     * @return
//     */
//    private AuthenticationFailureHandler getFileureHandler(){
//        return (request, response, authentication) -> {
//            Result result = new Result(5000,"认证失败",null);
//            printJsonData(response,result);
//        };
//    }



    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(daoAuthenticationProvider());
        providers.add(customAuthenticationProvider2());

        return new ProviderManager(providers);
    }

    public static <T> Map<String, Object> json2map(String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(jsonString, Map.class);    }
}
