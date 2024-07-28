package dio.lab.padroes.projeto_spring.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity( name = "TB_CLIENTE" )
public class ClienteEntity {

  @Id
  @GeneratedValue( strategy = GenerationType.AUTO )
  private Long                  id;
  private String                nome;
  @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.PERSIST )
  private EnderecoClienteEntity endereco;

  public Long getId() {
    return id;
  }

  public void setId( Long id ) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome( String nome ) {
    this.nome = nome;
  }

  public EnderecoClienteEntity getEndereco() {
    return endereco;
  }

  public void setEndereco( EnderecoClienteEntity endereco ) {
    this.endereco = endereco;
  }

}
