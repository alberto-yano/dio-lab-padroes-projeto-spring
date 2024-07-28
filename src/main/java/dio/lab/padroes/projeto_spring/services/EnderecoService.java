package dio.lab.padroes.projeto_spring.services;

import dio.lab.padroes.projeto_spring.domain.EnderecoEntity;

public interface EnderecoService {

  public EnderecoEntity getEnderecoByCep( String cep );

}
