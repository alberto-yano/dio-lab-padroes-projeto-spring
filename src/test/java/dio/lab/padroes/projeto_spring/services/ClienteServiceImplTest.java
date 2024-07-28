package dio.lab.padroes.projeto_spring.services;

import dio.lab.padroes.projeto_spring.controllers.dtos.request.ClienteRequestDto;
import dio.lab.padroes.projeto_spring.controllers.dtos.request.EnderecoRequestDto;
import dio.lab.padroes.projeto_spring.controllers.dtos.response.ClienteResponseDto;
import dio.lab.padroes.projeto_spring.domain.ClienteEntity;
import dio.lab.padroes.projeto_spring.domain.EnderecoClienteEntity;
import dio.lab.padroes.projeto_spring.domain.EnderecoEntity;
import dio.lab.padroes.projeto_spring.domain.enumeration.UfEnum;
import dio.lab.padroes.projeto_spring.repositories.ClienteRepository;
import dio.lab.padroes.projeto_spring.repositories.EnderecoClienteRepository;
import dio.lab.padroes.projeto_spring.services.impl.ClienteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles( "local" )
@ExtendWith( SpringExtension.class )
public class ClienteServiceImplTest {

  @Mock
  private ClienteRepository         clienteRepository;
  @Mock
  private EnderecoService           enderecoService;
  @Mock
  private EnderecoClienteRepository enderecoClienteRepository;

  @InjectMocks
  private ClienteServiceImpl clienteService;

  @Test
  public void buscarTodosTest() {
    List<ClienteEntity> clienteEntityList = new ArrayList<>();

    Mockito.doReturn( clienteEntityList )
           .when( this.clienteRepository )
           .findAll();

    Assertions.assertEquals( 0, clienteService.buscarTodos()
                                              .size() );

    clienteEntityList.add( getClienteEntityFromDb() );

    Assertions.assertEquals( 1, clienteService.buscarTodos()
                                              .size() );
  }

  @Test
  public void buscarPorIdTeste() {
    Assertions.assertThrows( NoSuchElementException.class, () -> clienteService.buscarPorId( 1l ) );

    Mockito.doReturn( Optional.of( getClienteEntityFromDb() ) )
           .when( clienteRepository )
           .findById( 1l );

    Assertions.assertNotNull( clienteService.buscarPorId( 1l ) );
  }

  @Test
  public void inserirTest() {
    ClienteRequestDto clienteRequestDto = new ClienteRequestDto();

    Assertions.assertThrows( IllegalArgumentException.class, () -> clienteService.inserir( clienteRequestDto ) );

    clienteRequestDto.setNome( "Nome" );
    clienteRequestDto.setEndereco( new EnderecoRequestDto() );
    Assertions.assertThrows( IllegalArgumentException.class, () -> clienteService.inserir( clienteRequestDto ) );

    clienteRequestDto.getEndereco()
                     .setCep( "01001000" );

    ClienteEntity clienteEntity = getClienteEntityFromDb();
    Mockito.doReturn( clienteEntity )
           .when( clienteRepository )
           .save( any() );

    System.out.println( clienteService.inserir( clienteRequestDto ) );
  }

  @Test
  public void atualizarTest() {
    ClienteRequestDto clienteRequestDto = new ClienteRequestDto();
    clienteRequestDto.setEndereco( new EnderecoRequestDto() );

    Assertions.assertThrows( NoSuchElementException.class, () -> clienteService.atualizar( 0l, clienteRequestDto ) );

    EnderecoClienteEntity enderecoClienteEntity = new EnderecoClienteEntity();
    Mockito.doReturn( enderecoClienteEntity )
           .when( enderecoClienteRepository )
           .getReferenceById( 1l );

    EnderecoEntity enderecoEntity = getEnderecoEntityFromDb();
    Mockito.doReturn( enderecoEntity )
           .when( enderecoService )
           .getEnderecoByCep( "01001000" );

    ClienteEntity clienteEntity = getClienteEntityFromDb();
    Mockito.doReturn( Optional.of( clienteEntity ) )
           .when( clienteRepository )
           .findById( 1l );
    Mockito.doReturn( clienteEntity )
           .when( clienteRepository )
           .save( any() );
    clienteRequestDto.setNome( "Novo Nome" );
    clienteRequestDto.getEndereco()
                     .setCep( "01001000" );
    clienteRequestDto.getEndereco()
                     .setComplemento( "Complemento" );

    ClienteResponseDto clienteResponseDto = clienteService.atualizar( 1l, clienteRequestDto );
    Assertions.assertEquals( clienteRequestDto.getNome(), clienteResponseDto.getNome() );
    Assertions.assertEquals( clienteRequestDto.getEndereco()
                                              .getComplemento(), clienteResponseDto.getEndereco()
                                                                                   .getComplemento() );
  }

  @Test
  public void deletarTest() {
    Assertions.assertThrows( NoSuchElementException.class, () -> clienteService.deletar( 1l ) );
    Mockito.doReturn( true )
           .when( clienteRepository )
           .existsById( 1l );
    Assertions.assertAll( () -> clienteService.deletar( 1l ) );
  }

  private ClienteEntity getClienteEntityFromDb() {
    ClienteEntity result = new ClienteEntity();
    result.setId( 1l );
    result.setNome( "Nome" );
    result.setEndereco( new EnderecoClienteEntity() );

    result.getEndereco()
          .setId( 1l );
    result.getEndereco()
          .setComplemento( "Complemento" );
    result.getEndereco()
          .setEndereco( getEnderecoEntityFromDb() );

    return result;
  }

  private EnderecoEntity getEnderecoEntityFromDb() {
    EnderecoEntity result = new EnderecoEntity();
    result.setId( 1l );
    result.setCep( "01001-000" );
    result.setLogradouro( "Praça da Sé" );
    result.setComplemento( "lado ímpar" );
    result.setUnidade( "" );
    result.setBairro( "Sé" );
    result.setUf( UfEnum.SP );
    result.setIbge( "3550308" );
    result.setGia( "1004" );
    result.setDdd( "11" );
    result.setSiafi( "7107" );

    return result;
  }

}
