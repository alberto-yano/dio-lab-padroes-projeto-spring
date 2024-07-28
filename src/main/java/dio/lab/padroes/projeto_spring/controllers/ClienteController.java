package dio.lab.padroes.projeto_spring.controllers;

import dio.lab.padroes.projeto_spring.controllers.dtos.request.ClienteRequestDto;
import dio.lab.padroes.projeto_spring.controllers.dtos.response.ClienteResponseDto;
import dio.lab.padroes.projeto_spring.services.ClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Esse {@link RestController} representa nossa <b>Facade</b>, pois abstrai toda a complexidade de integrações (Banco de
 * Dados H2 e API do ViaCEP) em uma interface simples e coesa (API REST).
 */
@RestController
@RequiredArgsConstructor
@RequestMapping( "/v1/cliente" )
@Tag( name = "Clientes" )
public class ClienteController {

  private final ClienteService clienteService;

  @GetMapping
  public ResponseEntity<List<ClienteResponseDto>> buscarTodos() {
    return ResponseEntity.ok( clienteService.buscarTodos() );
  }

  @GetMapping( "/{id}" )
  public ResponseEntity<ClienteResponseDto> buscarPorId( @PathVariable Long id ) {
    return ResponseEntity.ok( clienteService.buscarPorId( id ) );
  }

  @PostMapping
  public ResponseEntity<ClienteResponseDto> inserir( @Valid @RequestBody ClienteRequestDto cliente ) {
    return ResponseEntity.status( HttpStatus.CREATED )
                         .body( clienteService.inserir( cliente ) );
  }

  @PutMapping( "/{id}" )
  public ResponseEntity<ClienteResponseDto> atualizar( @PathVariable Long id,
                                                       @Valid @RequestBody ClienteRequestDto cliente ) {
    return ResponseEntity.ok( clienteService.atualizar( id, cliente ) );
  }

  @DeleteMapping( "/{id}" )
  public ResponseEntity<Void> deletar( @PathVariable Long id ) {
    clienteService.deletar( id );
    return ResponseEntity.noContent()
                         .build();
  }

}
