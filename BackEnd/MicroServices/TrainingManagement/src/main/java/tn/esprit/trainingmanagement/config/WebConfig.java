package tn.esprit.trainingmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Applique CORS à toutes les routes
                .allowedOrigins("http://localhost:4200")  // Autorise les requêtes venant de localhost:4200
                .allowedMethods("GET", "POST", "PUT", "DELETE");  // Autorise les méthodes HTTP nécessaires
    }
}
