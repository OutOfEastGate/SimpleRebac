package xyz.wanghongtao.rebac.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author wanghongtao
 * @data 2023/7/23 18:08
 */
@Slf4j
@Configuration
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("#处理请求：{}", request.getRequestURI());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
