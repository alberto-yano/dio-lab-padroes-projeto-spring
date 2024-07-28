package dio.lab.padroes.projeto_spring.services.impl;

import dio.lab.padroes.projeto_spring.domain.EnderecoEntity;
import dio.lab.padroes.projeto_spring.repositories.EnderecoRepository;
import dio.lab.padroes.projeto_spring.services.EnderecoService;
import dio.lab.padroes.projeto_spring.services.ViaCepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementação da <b>Strategy</b> {@link EnderecoService}, a qual é injetada pelo Spring no construtor. Com isso essa
 * classe /e um {@link Service}, ela será tratada como um <b>Singleton</b>.
 */
@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

  private final EnderecoRepository enderecoRepository;
  private final ViaCepService      viaCepService;

  @Transactional( propagation = Propagation.REQUIRES_NEW )
  public EnderecoEntity getEnderecoByCep( String cep ) {
    final String cepFinal = cep.substring( 0, 5 ) + "-" + cep.substring( 5 );
    return enderecoRepository.findByCep( cepFinal )
                             .orElseGet( () -> enderecoRepository.save( viaCepService.getEnderecoByCep( cepFinal ) ) );
  }

}
