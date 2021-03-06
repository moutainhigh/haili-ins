package com.haili.ins.common.spring.webmvc.filter;

import com.haili.ins.common.constants.HailiInsConstant;
import com.haili.ins.common.constants.HttpHeaderConstant;
import com.haili.ins.common.utils.JSONUtil;
import com.haili.ins.common.utils.JWTUtils;
import com.haili.ins.common.vo.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: leon
 * @Date: 2019/3/21 15:07
 * @Version 1.0
 */
@Slf4j
@Order(0)
public class SecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info(" 开始SecurityFilter调用 : ");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        servletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String url = request.getRequestURI();
        log.info("请求uri: " + url);

        String token = request.getHeader(HttpHeaderConstant.SECURITY_TOKEN);
        if (StringUtils.isNotEmpty(token)) {
            log.info("接收到的token: " + token);
            String responseCode = JWTUtils.verifyToken(token);
            if (HailiInsConstant.SUCCESS.equals(responseCode)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                servletResponse.getWriter().print(JSONUtil.toJson(new ResultInfo(HailiInsConstant.VERIFY_ERROR, "服务调用token验证失败")));
            }
        } else {
            servletResponse.getWriter().print(JSONUtil.toJson(new ResultInfo(HailiInsConstant.VERIFY_ERROR, "服务调用token不存在")));

        }


    }

}
