package br.edu.fatecfranca.api.exceptions;

public class InvalidCrudDataException extends RuntimeException {

    public InvalidCrudDataException(String message) {
        super(message);
    }
}
