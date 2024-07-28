package dio.lab.padroes.projeto_spring.domain;

import dio.lab.padroes.projeto_spring.domain.enumeration.UfEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
@Entity( name = "TB_ENDERECO_CLIENTE" )
public class EnderecoClienteEntity {

  @Id
  @GeneratedValue( strategy = GenerationType.AUTO )
  private Long           id;
  @ManyToOne( fetch = FetchType.EAGER, optional = false )
  private EnderecoEntity endereco;
  private String         complemento;

  public Long getIdEndereco() {
    return endereco.getId();
  }

  public String getCep() {
    return endereco.getCep();
  }

  public String getLogradouro() {
    return StringUtils.hasText( endereco.getComplemento() ) ?
      endereco.getLogradouro() + " - " + endereco.getComplemento() : endereco.getLogradouro();
  }

  public String getUnidade() {
    return endereco.getUnidade();
  }

  public String getBairro() {
    return endereco.getBairro();
  }

  public String getLocalidade() {
    return endereco.getLocalidade();
  }

  public UfEnum getUf() {
    return endereco.getUf();
  }

  public String getDdd() {
    return endereco.getDdd();
  }

  public String getIbge() {
    return endereco.getIbge();
  }

  public String getGia() {
    return endereco.getGia();
  }

  public String getSiafi() {
    return endereco.getSiafi();
  }

}
