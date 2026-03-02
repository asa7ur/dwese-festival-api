package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Value("${UPLOAD_PATH}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (uploadPath != null && !uploadPath.isEmpty()) {
            logger.info("UPLOAD_PATH configurado correctamente: {}", uploadPath);
            registry.addResourceHandler("/uploads/**")
                    .addResourceLocations("file:" + uploadPath + "/");
        } else {
            logger.error("La variable de entorno UPLOAD_PATH no está configurada o está vacía.");
        }
    }
}