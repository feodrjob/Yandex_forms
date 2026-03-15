package org.example.yandex_forms;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Yandex Forms API")
                        .description("API для управления формами, вопросами и ответами")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("StudLabTeam")
                                .email("feodor070305@gmail.com")));
    }
}