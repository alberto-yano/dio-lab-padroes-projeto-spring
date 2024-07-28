package dio.lab.padroes.projeto_spring.config;

import io.swagger.v3.oas.models.info.Contact;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties( "projeto-spring" )
public class ProjetoSpringProperties {

  private Swagger swagger = new Swagger();

  @Getter
  @Setter
  public static class Swagger {

    private String  title;
    private String  description;
    private Contact contact = new Contact();

  }

}
