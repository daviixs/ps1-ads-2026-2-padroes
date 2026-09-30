package br.edu.fatecfranca.api.audit;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.edu.fatecfranca.api.events.CrudOperationEvent;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CrudOperationLog {

    private final List<CrudOperationEvent> operations = new CopyOnWriteArrayList<>();

    public void register(CrudOperationEvent event) {
        operations.add(event);
    }

    public List<CrudOperationEvent> findAll() {
        return List.copyOf(operations);
    }

    public void clear() {
        operations.clear();
    }
}
