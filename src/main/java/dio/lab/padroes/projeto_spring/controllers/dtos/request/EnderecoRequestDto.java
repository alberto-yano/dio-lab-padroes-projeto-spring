package dio.lab.padroes.projeto_spring.controllers.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EnderecoRequestDto {

  @Pattern( regexp = "\\d{8}", message = "O CEP deve conter apenas números e tamanho de 8 dígitos." )
  @NotNull( message = "O campo CEP é obrigatório." )
  private String cep;
  private String complemento;

}
