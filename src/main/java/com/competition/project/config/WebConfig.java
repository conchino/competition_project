package com.competition.project.config;
import com.competition.project.handler.MyHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class WebConfig implements WebMvcConfigurer{
    @Autowired
    private MyHandlerInterceptor myHandlerInterceptor;

    /*
     *   配置跨域请求解析
     */
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
//                .allowedOrigins("*")        // 此方法在 springboot-2.4.0 后已无法使用, 使用 .allowedOriginPatterns("*") 替代
                    .allowedOriginPatterns("*")
                    .allowedMethods("*")
                    .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                    .allowCredentials(true)
                .allowedHeaders("*")
                .maxAge(3600 * 24);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 设置拦截器放行路径
        registry.addInterceptor(myHandlerInterceptor)
                .excludePathPatterns(
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/v2/api-docs/**",
                        "/webjars/springfox-swagger-ui/**",
                        "/login",
                        "/project/employees/**",
                        "/project/login-account/applyAccount/"
                );
        }
}
