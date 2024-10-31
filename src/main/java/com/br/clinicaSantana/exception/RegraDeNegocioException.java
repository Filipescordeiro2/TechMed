package com.br.clinicaSantana.exception;

/**
 * Exceção personalizada para representar erros de regra de negócio no sistema.
 */
public class RegraDeNegocioException extends RuntimeException {

    /**
     * Construtor que aceita uma mensagem de erro.
     *
     * @param message Mensagem de erro detalhada.
     */
    public RegraDeNegocioException(String message) {
        super(message);
    }
}