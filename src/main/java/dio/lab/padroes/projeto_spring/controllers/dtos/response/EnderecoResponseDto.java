package dio.lab.padroes.projeto_spring.controllers.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import dio.lab.padroes.projeto_spring.domain.enumeration.UfEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude( JsonInclude.Include.NON_NULL )
public class EnderecoResponseDto {

  private String cep;
  private String logradouro;
  private String complemento;
  private String unidade;
  private String bairro;
  private String localidade;
  private UfEnum uf;
  private String ddd;

}
