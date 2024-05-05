package xyz.wanghongtao.rebac.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.wanghongtao.rebac.object.runtime.LoginInterceptorRuntime;
import xyz.wanghongtao.rebac.util.JwtUtil;

/**
 * @author wanghongtao
 * @data 2023/7/23 18:08
 */
@Slf4j
@Configuration
public class LoginInterceptor implements HandlerInterceptor {
    private final LoginInterceptorRuntime interceptorRuntime;

    public LoginInterceptor(LoginInterceptorRuntime interceptorRuntime) {
      this.interceptorRuntime = interceptorRuntime;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
      String requestURI = request.getRequestURI();
      log.info("#处理请求：{}", request.getRequestURI());
      if (requestURI.startsWith("/store/image")) {
        return true;
      }
      if (requestURI.startsWith("/document")) {
        return true;
      }
      if (requestURI.contains(".css") || requestURI.contains(".js") || requestURI.contains("/json")) {
        return true;
      }
      if (requestURI.equals("/") || requestURI.equals("/favicon.ico") || requestURI.equals("/index.html") || requestURI.startsWith("/static/") || requestURI.startsWith("/admin/")|| requestURI.startsWith("/show/")) {
        return true;
      }
      if (requestURI.equals("/login") || requestURI.equals("/logo192.png")) {
        return true;
      }
      if (request.getRequestURI().equals("/user/login") || request.getRequestURI().equals("/user/register")) {
        return true;
      }
      String token = request.getHeader("token");
      String method = request.getMethod();
      if(method.equals(HttpMethod.OPTIONS.name())) {
        return true;
      }
      String usernameByToken = JwtUtil.getUsernameByToken(token);
      log.info("#登陆用户：{}", usernameByToken);
//      String tokenFromCache = interceptorRuntime.getTokenByUsername(usernameByToken);
      return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
