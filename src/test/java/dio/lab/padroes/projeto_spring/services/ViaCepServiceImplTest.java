package dio.lab.padroes.projeto_spring.services;

import dio.lab.padroes.projeto_spring.domain.EnderecoEntity;
import dio.lab.padroes.projeto_spring.exceptions.NegocioException;
import dio.lab.padroes.projeto_spring.repositories.ViaCepRepository;
import dio.lab.padroes.projeto_spring.services.impl.ViaCepServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles( "local" )
@ExtendWith( SpringExtension.class )
public class ViaCepServiceImplTest {

  @Mock
  private ViaCepRepository viaCepRepository;

  @InjectMocks
  private ViaCepServiceImpl viaCepService;

  @Test
  public void getEnderecoByCepTest() {
    EnderecoEntity endereco = new EnderecoEntity();

    Mockito.doReturn( endereco )
           .when( viaCepRepository )
           .getEnderecoByCep( "01000100" );
    Assertions.assertThrows( NegocioException.class, () -> viaCepService.getEnderecoByCep( "01000100" ) );

    endereco.setCep( "01001-000" );
    Assertions.assertEquals( endereco, viaCepService.getEnderecoByCep( "01000100" ) );
  }

}
