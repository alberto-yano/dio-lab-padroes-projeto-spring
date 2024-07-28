package dio.lab.padroes.projeto_spring.services.impl;

import dio.lab.padroes.projeto_spring.domain.EnderecoEntity;
import dio.lab.padroes.projeto_spring.exceptions.NegocioException;
import dio.lab.padroes.projeto_spring.repositories.ViaCepRepository;
import dio.lab.padroes.projeto_spring.services.ViaCepService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViaCepServiceImpl implements ViaCepService {

  private final ViaCepRepository viaCepRepository;

  public EnderecoEntity getEnderecoByCep( String cep ) {
    EnderecoEntity result = this.viaCepRepository.getEnderecoByCep( cep );
    if( StringUtils.isEmpty( result.getCep() ) ) {
      throw new NegocioException( "CEP inválido." );
    }
    return result;
  }

}
