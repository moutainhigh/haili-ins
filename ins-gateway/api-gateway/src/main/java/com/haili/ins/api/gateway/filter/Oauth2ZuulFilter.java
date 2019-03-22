package com.haili.ins.api.gateway.filter;


import com.haili.ins.api.gateway.properties.PermitAllUrlProperties;
import com.haili.ins.common.constants.HttpHeaderConstant;
import com.haili.ins.enums.RequestSourceEnum;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.RIBBON_ROUTING_FILTER_ORDER;


public class Oauth2ZuulFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(Oauth2ZuulFilter.class);

    @Autowired
    private PermitAllUrlProperties permitAllUrlProperties;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return RIBBON_ROUTING_FILTER_ORDER   ;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();

        HttpServletRequest request = requestContext.getRequest();
        String url = request.getRequestURI();
        String requestMethod = requestContext.getRequest().getMethod();

        if(permitAllUrlProperties.isPermitAllUrl(url)){
            return false;
        }
        return true;

    }

    @Override
    public Object run() {
        logger.info(" 开始OAuth2Zuul路由调用 : ");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String routeName = request.getRequestURL().toString();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String token = details.getTokenType() + "" + details.getTokenValue();
        System.out.println(token);

        requestContext.addZuulRequestHeader(HttpHeaders.AUTHORIZATION, token);
        requestContext.addZuulRequestHeader(HttpHeaderConstant.REQUEST_SOURCE, RequestSourceEnum.GATEWAY.getCode());

        logger.info(" 开始OAuth2Zuul路由调用 : " + routeName);
        return null;

    }


}
