package dio.lab.padroes.projeto_spring.repositories;

import dio.lab.padroes.projeto_spring.domain.EnderecoClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoClienteRepository extends JpaRepository<EnderecoClienteEntity, Long> {

}
