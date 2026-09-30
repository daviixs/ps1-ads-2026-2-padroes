package br.edu.fatecfranca.api.events;

public record CrudOperationEvent(String resource, CrudOperation operation, Long resourceId) {
}
