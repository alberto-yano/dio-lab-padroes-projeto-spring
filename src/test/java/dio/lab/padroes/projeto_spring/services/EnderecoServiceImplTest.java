package dio.lab.padroes.projeto_spring.services;

import dio.lab.padroes.projeto_spring.domain.EnderecoEntity;
import dio.lab.padroes.projeto_spring.repositories.EnderecoRepository;
import dio.lab.padroes.projeto_spring.services.impl.EnderecoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles( "local" )
@ExtendWith( SpringExtension.class )
public class EnderecoServiceImplTest {

  @Mock
  private EnderecoRepository enderecoRepository;
  @Mock
  private ViaCepService      viaCepService;

  @InjectMocks
  private EnderecoServiceImpl enderecoService;

  @Test
  public void getEnderecoByCepTest() {
    EnderecoEntity           endereco               = new EnderecoEntity();
    Optional<EnderecoEntity> enderecoEntityOptional = Optional.of( endereco );

    Assertions.assertNull( enderecoService.getEnderecoByCep( "01001000" ) );

    Mockito.doReturn( endereco )
           .when( viaCepService )
           .getEnderecoByCep( "01001-000" );
    Mockito.doReturn( endereco )
           .when( enderecoRepository )
           .save( endereco );
    Assertions.assertEquals( endereco, enderecoService.getEnderecoByCep( "01001000" ) );

    Mockito.doReturn( enderecoEntityOptional )
           .when( enderecoRepository )
           .findByCep( "01001-000" );
    Assertions.assertEquals( endereco, enderecoService.getEnderecoByCep( "01001000" ) );
  }

}
