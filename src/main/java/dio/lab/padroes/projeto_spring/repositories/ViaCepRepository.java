package dio.lab.padroes.projeto_spring.repositories;

import dio.lab.padroes.projeto_spring.domain.EnderecoEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient( name = "viaCep", url = "${projeto-spring.viacep-url}" )
public interface ViaCepRepository {

  @GetMapping( "/{cep}/json/" )
  EnderecoEntity getEnderecoByCep( @PathVariable( "cep" ) String cep );

}
