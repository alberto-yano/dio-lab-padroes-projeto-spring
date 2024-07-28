package dio.lab.padroes.projeto_spring.services;

import dio.lab.padroes.projeto_spring.domain.EnderecoEntity;

public interface ViaCepService {

  EnderecoEntity getEnderecoByCep( String cep );

}
