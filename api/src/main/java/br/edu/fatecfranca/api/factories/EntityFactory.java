package br.edu.fatecfranca.api.factories;

public abstract class EntityFactory<T> {

    protected abstract T newEntity();
}
