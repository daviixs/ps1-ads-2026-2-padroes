package br.edu.fatecfranca.api.observers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.edu.fatecfranca.api.audit.CrudOperationLog;
import br.edu.fatecfranca.api.events.CrudOperation;
import br.edu.fatecfranca.api.events.CrudOperationEvent;

class CrudOperationObserverTests {

    @Test
    void registersObservedCrudOperation() {
        CrudOperationLog log = new CrudOperationLog();
        CrudOperationEvent event = new CrudOperationEvent("car", CrudOperation.UPDATED, 1L);

        new CrudOperationObserver(log).onOperation(event);

        assertEquals(1, log.findAll().size());
        assertEquals(event, log.findAll().getFirst());
    }
}
