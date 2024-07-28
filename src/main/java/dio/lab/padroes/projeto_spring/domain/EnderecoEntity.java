package dio.lab.padroes.projeto_spring.domain;

import dio.lab.padroes.projeto_spring.domain.enumeration.UfEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity( name = "TB_ENDERECO" )
public class EnderecoEntity {

  @Id
  @GeneratedValue( strategy = GenerationType.AUTO )
  private Long   id;
  @Column( unique = true )
  private String cep;
  private String logradouro;
  private String complemento;
  private String unidade;
  private String bairro;
  private String localidade;
  private UfEnum uf;
  private String ddd;
  private String ibge;
  private String gia;
  private String siafi;

}
