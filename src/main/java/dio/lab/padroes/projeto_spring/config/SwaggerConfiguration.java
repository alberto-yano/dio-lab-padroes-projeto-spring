package dio.lab.padroes.projeto_spring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {

  private final ProjetoSpringProperties properties;

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().info( new Info().title( properties.getSwagger()
                                                           .getTitle() )
                                         .description( properties.getSwagger()
                                                                 .getDescription() )
                                         .contact( properties.getSwagger()
                                                             .getContact() ) );
  }

}
