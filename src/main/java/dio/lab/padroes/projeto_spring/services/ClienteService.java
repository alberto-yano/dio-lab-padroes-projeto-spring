package dio.lab.padroes.projeto_spring.services;

import dio.lab.padroes.projeto_spring.controllers.dtos.request.ClienteRequestDto;
import dio.lab.padroes.projeto_spring.controllers.dtos.response.ClienteResponseDto;

import java.util.List;

/**
 * Interface que define o padrão <b>Strategy</b> no domínio de cliente. Com isso, se necessário, podemos ter multiplas
 * implementações dessa mesma interface.
 *
 * @author falvojr
 */
public interface ClienteService {

  List<ClienteResponseDto> buscarTodos();

  ClienteResponseDto buscarPorId( Long id );

  ClienteResponseDto inserir( ClienteRequestDto cliente );

  ClienteResponseDto atualizar( Long id, ClienteRequestDto cliente );

  void deletar( Long id );

}
