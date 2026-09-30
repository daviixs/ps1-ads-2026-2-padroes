package br.edu.fatecfranca.api.observers;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.edu.fatecfranca.api.audit.CrudOperationLog;
import br.edu.fatecfranca.api.events.CrudOperationEvent;

@Component
public class CrudOperationObserver {

    private final CrudOperationLog operationLog;

    public CrudOperationObserver(CrudOperationLog operationLog) {
        this.operationLog = operationLog;
    }

    @EventListener
    public void onOperation(CrudOperationEvent event) {
        operationLog.register(event);
    }
}
