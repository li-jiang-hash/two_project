package com.aaa.sso.filter;

import com.aaa.sso.token.MultipleLoginAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

public class MultipleLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private String loginParameter = "username";
    private boolean postOnly = true;


    public MultipleLoginAuthenticationFilter() {
        super();
    }

    /**
     * 复写新的过滤器方法,并构造新的authenticationToken
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        // 获取登录传递的账号密码 telephone
        String username = request.getParameter("telephone");//obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();
        MultipleLoginAuthenticationToken authRequest = new MultipleLoginAuthenticationToken(username,password);
        this.setDetails(request, authRequest);
        // 用户寻找自定义的校验Provider
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    /**
     * @param request     获取请求的参数
     * @param authRequest
     */
    protected void setDetails(HttpServletRequest request, MultipleLoginAuthenticationToken authRequest) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        authRequest.setDetails(map);
    }


    public void setLoginParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.loginParameter = usernameParameter;
    }


    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getLoginParameter() {
        return loginParameter;
    }
}
