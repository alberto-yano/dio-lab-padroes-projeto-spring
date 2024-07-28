package dio.lab.padroes.projeto_spring.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
@JsonInclude( JsonInclude.Include.NON_NULL )
@Schema( name = "Erro" )
public class ResponseError {

  private int                 code;
  private String              description;
  @Builder.Default
  private Map<String, String> fields = new HashMap<>();

}
