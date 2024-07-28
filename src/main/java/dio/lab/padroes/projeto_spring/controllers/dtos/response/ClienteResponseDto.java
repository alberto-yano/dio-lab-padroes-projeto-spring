package dio.lab.padroes.projeto_spring.controllers.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude( JsonInclude.Include.NON_NULL )
public class ClienteResponseDto {

  private Long                id;
  private String              nome;
  private EnderecoResponseDto endereco;

}
