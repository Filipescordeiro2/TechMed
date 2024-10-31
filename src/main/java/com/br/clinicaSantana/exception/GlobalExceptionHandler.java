package com.br.clinicaSantana.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Classe responsável por tratar exceções globais no sistema.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata exceções do tipo RegraDeNegocioException.
     *
     * @param ex Exceção lançada.
     * @param request Detalhes da requisição onde a exceção ocorreu.
     * @return Resposta HTTP 400 (Bad Request) com a mensagem da exceção.
     */
    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<Object> handleRegraDeNegocioException(RegraDeNegocioException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}