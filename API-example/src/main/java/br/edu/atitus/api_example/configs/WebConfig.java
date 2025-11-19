package br.edu.atitus.api_example.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 1. Configuração de CORS (Cross-Origin Resource Sharing)
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Permite que qualquer Front-end (origem '*') acesse qualquer endpoint ('/**')
        registry.addMapping("/**")
                .allowedOrigins("*") // Permite acesso de qualquer domínio
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
    }
    
    // 2. Configuração de Recursos Estáticos (Servir as Fotos)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapeia a URL /uploads/** para a pasta física 'uploads' no sistema
        // Isso permite que o Front-end acesse as fotos através de http://localhost:8080/uploads/nome_da_foto.jpg
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/"); // O prefixo 'file:' aponta para o sistema de arquivos
    }
}
