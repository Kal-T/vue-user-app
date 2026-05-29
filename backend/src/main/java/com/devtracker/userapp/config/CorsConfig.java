package com.devtracker.userapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

/**
 * ==============================================================================
 * CORS TROUBLESHOOTING CHECKLIST
 * ==============================================================================
 * If you encounter CORS issues, audit the following common configurations:
 * 
 * 1. Missing Credentials Conflict:
 *    - Cause: If allowCredentials is set to true on the backend, the frontend MUST send
 *      credentials, and allowedOrigins CANNOT contain wildcards ("*").
 *    - Solution: Ensure exact allowed origin lists (e.g. http://localhost:5173) are mapped,
 *      and the frontend Axios client is configured to allow credential mapping.
 * 
 * 2. Wildcard & Credentials Mismatch:
 *    - Cause: Setting both allowedOrigins("*") and allowCredentials(true) will trigger
 *      browser safety failures (Access-Control-Allow-Origin cannot be wildcard when credentials enabled).
 *    - Solution: Specify exact domain origins instead of "*".
 * 
 * 3. Preflight OPTIONS 401 / 403 Forbidden:
 *    - Cause: Security firewalls or token interceptors blocking preflight OPTIONS queries.
 *    - Solution: Ensure OPTIONS requests are permitted without authentication and returned with a 200 OK.
 * 
 * 4. Missing Allowed Headers:
 *    - Cause: Frontend sending custom headers (e.g., Authorization) which are not explicitly
 *      registered inside allowedHeaders on the backend.
 *    - Solution: Add custom headers to the allowedHeaders configuration list.
 * ==============================================================================
 */
@Configuration
public class CorsConfig {

    @Value("${app.cors.allowed-origin}")
    private String allowedOrigin;

    /**
     * APPROACH 1: Servlet Filter-Based CORS (CorsFilter Bean)
     * 
     * This defines a servlet-level filter that intercepts requests early in the HTTP container filter chain.
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowedOrigins(List.of(allowedOrigin));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L); // Cache preflight response in browser for 1 hour
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * APPROACH 2: Spring MVC-Based CORS (WebMvcConfigurer Bean)
     * 
     * This defines a Spring MVC handler interceptor configuration.
     */
    @Bean
    public WebMvcConfigurer mvcCorsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigin)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("Authorization", "Content-Type")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }

    /**
     * ==============================================================================
     * PRECEDENCE EXPLANATION
     * ==============================================================================
     * When both the Filter-based CorsFilter bean and the MVC-based WebMvcConfigurer exist:
     * 
     * 1. The CorsFilter BEAN TAKES PRECEDENCE.
     * 2. This is because Servlet Filters operate at the servlet container level (e.g. Tomcat),
     *    intercepting incoming requests before they ever reach the Spring MVC DispatcherServlet
     *    (where WebMvcConfigurer interceptor mappings are registered).
     * 3. Therefore, preflight OPTIONS requests are handled and completed by CorsFilter first. 
     *    WebMvcConfigurer serves as a fallback approach but is bypassed when the filter handles the request.
     * ==============================================================================
     */
}
