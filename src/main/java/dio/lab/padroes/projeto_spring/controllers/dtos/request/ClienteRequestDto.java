package dio.lab.padroes.projeto_spring.controllers.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClienteRequestDto {

  @NotNull( message = "Nome não pode ser nulo." )
  @Size( min = 1, message = "Nome não pode estar vazio." )
  private String             nome;
  @Valid
  @NotNull( message = "Endereço não pode ser nulo." )
  private EnderecoRequestDto endereco;

}
