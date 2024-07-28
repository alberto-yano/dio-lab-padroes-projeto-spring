package dio.lab.padroes.projeto_spring.config;

import dio.lab.padroes.projeto_spring.exceptions.NegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Classe para capturar
 */
@RestControllerAdvice
public class ResourceExceptionHandler {

  @ResponseStatus( HttpStatus.BAD_REQUEST )
  @ExceptionHandler( MethodArgumentNotValidException.class )
  public ResponseError handleMethodArgumentNotValidException( MethodArgumentNotValidException ex ) {
    var msg = "Campos da requisição inválidos.";
    var responseError = ResponseError.builder()
                                     .code( HttpStatus.BAD_REQUEST.value() )
                                     .description( msg )
                                     .build();
    ex.getBindingResult()
      .getAllErrors()
      .forEach( e -> {
        String fieldName    = ( (FieldError) e ).getField();
        String errorMessage = e.getDefaultMessage();
        responseError.getFields()
                     .put( fieldName, errorMessage );
      } );
    return responseError;
  }

  @ResponseStatus( HttpStatus.BAD_REQUEST )
  @ExceptionHandler( MethodArgumentTypeMismatchException.class )
  public ResponseError handleMethodArgumentNotValidException( MethodArgumentTypeMismatchException ex ) {
    var                 msg    = "Tipo do parâmero inválido.";
    Map<String, String> fields = new HashMap<>();
    fields.put( "Nome do parâmetro", ex.getName() );
    fields.put( "Valor do parâmetro", Objects.toString( ex.getValue(), "null" ) );
    fields.put( "Detalhe", ex.getMessage() );
    return ResponseError.builder()
                        .code( HttpStatus.METHOD_NOT_ALLOWED.value() )
                        .description( msg )
                        .fields( fields )
                        .build();
  }

  @ResponseStatus( HttpStatus.NOT_FOUND )
  @ExceptionHandler( NoSuchElementException.class )
  public ResponseError handleNoSuchElementException( NoSuchElementException ex ) {
    var msg = "Registro não encontrado.";
    return ResponseError.builder()
                        .code( HttpStatus.NOT_FOUND.value() )
                        .description( msg )
                        .build();
  }

  @ResponseStatus( HttpStatus.METHOD_NOT_ALLOWED )
  @ExceptionHandler( HttpRequestMethodNotSupportedException.class )
  public ResponseError handleHttpRequestMethodNotSupportedException( HttpRequestMethodNotSupportedException ex ) {
    var                 msg    = "Método requisitado não suportado.";
    Map<String, String> fields = new HashMap<>();
    fields.put( "method", ex.getMethod() );
    return ResponseError.builder()
                        .code( HttpStatus.METHOD_NOT_ALLOWED.value() )
                        .description( msg )
                        .fields( fields )
                        .build();
  }

  @ResponseStatus( HttpStatus.BAD_REQUEST )
  @ExceptionHandler( HttpMessageNotReadableException.class )
  public ResponseError handleHttpMessageNotReadableException( HttpMessageNotReadableException ex ) {
    return ResponseError.builder()
                        .code( HttpStatus.BAD_REQUEST.value() )
                        .description( ex.getMessage() )
                        .build();
  }

  @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
  @ExceptionHandler( Exception.class )
  public ResponseError handleException( Exception ex ) {
    var msg = "Ocorreu um erro interno em nosso servidor: ";
    return ResponseError.builder()
                        .code( HttpStatus.INTERNAL_SERVER_ERROR.value() )
                        .description( msg + ex.getMessage() )
                        .build();
  }

  @ResponseStatus( HttpStatus.BAD_REQUEST )
  @ExceptionHandler( NegocioException.class )
  public ResponseError handleNegocioExceptionException( NegocioException ex ) {
    return ResponseError.builder()
                        .code( HttpStatus.BAD_REQUEST.value() )
                        .description( ex.getMessage() )
                        .build();
  }

}
