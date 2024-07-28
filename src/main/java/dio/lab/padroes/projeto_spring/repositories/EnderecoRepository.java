package dio.lab.padroes.projeto_spring.repositories;

import dio.lab.padroes.projeto_spring.domain.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {

  Optional<EnderecoEntity> findByCep( String cep );

}
