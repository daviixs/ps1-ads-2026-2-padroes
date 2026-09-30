package br.edu.fatecfranca.api.audit;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class CrudOperationLogTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void usesOneSpringSingletonInstance() {
        CrudOperationLog first = applicationContext.getBean(CrudOperationLog.class);
        CrudOperationLog second = applicationContext.getBean(CrudOperationLog.class);

        assertSame(first, second);
    }
}
