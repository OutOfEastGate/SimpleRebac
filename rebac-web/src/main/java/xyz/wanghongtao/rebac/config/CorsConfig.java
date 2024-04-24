package xyz.wanghongtao.rebac.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.wanghongtao.rebac.object.runtime.LoginInterceptorRuntime;

/**
 * @author wanghongtao
 * @data 2023/7/23 18:07
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
  private final LoginInterceptorRuntime interceptorRuntime;

  public CorsConfig(LoginInterceptorRuntime interceptorRuntime) {
    this.interceptorRuntime = interceptorRuntime;
  }

  @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(interceptorRuntime));
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // 允许访问的来源域名，可以使用通配符 *
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的 HTTP 方法
                .allowedHeaders("*") // 允许的请求头
                .maxAge(3600);
    }
}
