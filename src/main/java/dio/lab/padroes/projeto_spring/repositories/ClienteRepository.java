package dio.lab.padroes.projeto_spring.repositories;

import dio.lab.padroes.projeto_spring.domain.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

}
