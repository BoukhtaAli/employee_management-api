package ma.java.tutorials.employees.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@ConditionalOnProperty(value = "cors.enabled", havingValue = "true", matchIfMissing = false)
public class CorsConfiguration implements WebMvcConfigurer{

    @Value("${cors.allowedAddresses}")
    private List<String> allowedAddresses;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods("PUT", "DELETE", "GET", "POST")
                .allowedHeaders("*")
                .allowedOrigins(this.allowedAddresses.toArray(new String[this.allowedAddresses.size()]));
    }
}
