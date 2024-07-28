package dio.lab.padroes.projeto_spring;

import dio.lab.padroes.projeto_spring.config.ProjetoSpringProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties( ProjetoSpringProperties.class )
public class ProjetoSpringApplication {

  public static void main( String[] args ) {
    SpringApplication.run( ProjetoSpringApplication.class, args );
  }

}
