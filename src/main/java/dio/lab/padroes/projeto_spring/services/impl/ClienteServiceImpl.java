package dio.lab.padroes.projeto_spring.services.impl;

import dio.lab.padroes.projeto_spring.controllers.dtos.request.ClienteRequestDto;
import dio.lab.padroes.projeto_spring.controllers.dtos.response.ClienteResponseDto;
import dio.lab.padroes.projeto_spring.controllers.dtos.response.EnderecoResponseDto;
import dio.lab.padroes.projeto_spring.domain.ClienteEntity;
import dio.lab.padroes.projeto_spring.domain.EnderecoClienteEntity;
import dio.lab.padroes.projeto_spring.repositories.ClienteRepository;
import dio.lab.padroes.projeto_spring.repositories.EnderecoClienteRepository;
import dio.lab.padroes.projeto_spring.services.ClienteService;
import dio.lab.padroes.projeto_spring.services.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual é injetada pelo Spring no construtor. Com isso essa
 * classe /e um {@link Service}, ela será tratada como um <b>Singleton</b>.
 */
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

  private final ClienteRepository         clienteRepository;
  private final EnderecoService           enderecoService;
  private final EnderecoClienteRepository enderecoClienteRepository;

  @Override
  public List<ClienteResponseDto> buscarTodos() {
    return this.clienteRepository.findAll()
                                 .stream()
                                 .map( this::convertClienteEntity )
                                 .collect( Collectors.toList() );
  }

  @Override
  public ClienteResponseDto buscarPorId( final Long id ) {
    return convertClienteEntity( this.clienteRepository.findById( id )
                                                       .orElseThrow() );
  }

  @Override
  @Transactional
  public ClienteResponseDto inserir( final ClienteRequestDto cliente ) {
    Assert.notNull( cliente.getNome(), "Nome do Cliente está nulo." );
    Assert.notNull( cliente.getEndereco()
                           .getCep(), "CEP do Cliente está nulo." );
    return convertClienteEntity( salvar( cliente, new ClienteEntity() ) );
  }

  @Override
  @Transactional
  public ClienteResponseDto atualizar( final Long id, final ClienteRequestDto cliente ) {
    return convertClienteEntity( salvar( cliente, this.clienteRepository.findById( id )
                                                                        .orElseThrow() ) );
  }

  @Override
  public void deletar( final Long id ) {
    if( clienteRepository.existsById( id ) ) {
      clienteRepository.deleteById( id );
    }
    else {
      throw new NoSuchElementException();
    }
  }

  private ClienteResponseDto convertClienteEntity( ClienteEntity clienteEntity ) {
    return ClienteResponseDto.builder()
                             .id( clienteEntity.getId() )
                             .nome( clienteEntity.getNome() )
                             .endereco( EnderecoResponseDto.builder()
                                                           .cep( clienteEntity.getEndereco()
                                                                              .getCep() )
                                                           .logradouro( clienteEntity.getEndereco()
                                                                                     .getLogradouro() )
                                                           .complemento( clienteEntity.getEndereco()
                                                                                      .getComplemento() )
                                                           .unidade( clienteEntity.getEndereco()
                                                                                  .getUnidade() )
                                                           .bairro( clienteEntity.getEndereco()
                                                                                 .getBairro() )
                                                           .localidade( clienteEntity.getEndereco()
                                                                                     .getLocalidade() )
                                                           .uf( clienteEntity.getEndereco()
                                                                             .getUf() )
                                                           .ddd( clienteEntity.getEndereco()
                                                                              .getDdd() )
                                                           .build() )
                             .build();
  }

  private ClienteEntity salvar( ClienteRequestDto cliente, ClienteEntity clienteEntity ) {
    clienteEntity.setNome( cliente.getNome() );
    EnderecoClienteEntity enderecoClienteEntity = clienteEntity.getEndereco() ==
                                                  null ? new EnderecoClienteEntity() : this.enderecoClienteRepository.getReferenceById(
      clienteEntity.getEndereco()
                   .getId() );
    enderecoClienteEntity.setEndereco( this.enderecoService.getEnderecoByCep( cliente.getEndereco()
                                                                                     .getCep() ) );
    enderecoClienteEntity.setComplemento( cliente.getEndereco()
                                                 .getComplemento() );
    clienteEntity.setEndereco( enderecoClienteEntity );
    return this.clienteRepository.save( clienteEntity );
  }

}
