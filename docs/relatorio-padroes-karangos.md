# Relatorio Individual - Padroes de Projeto na Karangos

Repositorio: https://github.com/daviixs/ps1-ads-2026-2-padroes

## 1. Contexto e objetivo

O sistema Karangos ja possuia operacoes CRUD para clientes (`Customer`) e veiculos (`Car`). O repositorio entregue nao possui entidade, controller, servico ou migration para usuario; por isso, esta atividade foi aplicada somente aos dois CRUDs presentes no codigo-base.

O objetivo foi aplicar quatro padroes estudados - Factory Method, Decorator, Observer e Singleton - sem criar requisitos de negocio, tabelas, entidades ou endpoints. As rotas `POST`, `GET`, `PUT` e `DELETE` de clientes e veiculos foram preservadas.

## 2. Factory Method

### O que e e para que serve

Factory Method define um ponto de extensao para criar objetos. A classe abstrata declara o metodo de criacao e as classes concretas decidem qual objeto concreto instanciar. O padrao reduz o acoplamento entre quem precisa de um objeto e a construcao desse objeto.

### Onde, quando e como foi usado

Foi usado nos `POST`s dos CRUDs existentes. `CarFactory` e `CustomerFactory` herdam de `EntityFactory` e criam uma nova instancia a partir dos dados recebidos. O controller deixa de encaminhar diretamente a instancia desserializada para o servico.

```java
public abstract class EntityFactory<T> {
    protected abstract T newEntity();
}

@Component
public class CarFactory extends EntityFactory<Car> {
    @Override
    protected Car newEntity() {
        return new Car();
    }
}
```

No controller, a mesma rota de criacao usa a fabrica:

```java
return ResponseEntity.status(HttpStatus.CREATED)
        .body(service.create(factory.create(car)));
```

### Resultado alcancado e mudanca de requisito

Nao houve requisito novo. A criacao que ja existia continua funcionando, mas agora a construcao de `Car` e `Customer` esta centralizada e uma identificacao enviada pelo cliente nao e copiada para um novo cadastro.

## 3. Decorator

### O que e e para que serve

Decorator adiciona responsabilidades a um objeto por composicao, sem alterar sua classe principal. Ele tem a mesma interface do objeto decorado, delega a operacao e acrescenta um comportamento antes ou depois dela.

### Onde, quando e como foi usado

Os contratos `CarCrudService` e `CustomerCrudService` representam as operacoes que os controllers usam. `CarService` e `CustomerService` mantem a persistencia original. `ValidatingCarService` e `ValidatingCustomerService` decoram esses servicos e validam os campos obrigatorios antes de encaminhar `create` ou `update` ao servico original.

```java
@Service
@Primary
public class ValidatingCarService implements CarCrudService {
    private final CarCrudService delegate;

    @Override
    public Car create(Car car) {
        validate(car);
        return delegate.create(car);
    }
}
```

### Resultado alcancado e mudanca de requisito

Nao houve requisito novo. Os campos marcados como obrigatorios no modelo e no banco passam a ser verificados na camada de servico antes da persistencia. Dados invalidos recebem resposta `400 Bad Request`, mantendo os mesmos endpoints do CRUD.

## 4. Observer

### O que e e para que serve

Observer cria uma relacao de publicacao e assinatura entre objetos. Um objeto publica uma mudanca e os observadores interessados reagem sem que o publicador precise conhecer a implementacao deles.

### Onde, quando e como foi usado

Depois de criar, atualizar ou excluir um cliente ou veiculo, os servicos publicam `CrudOperationEvent`. `CrudOperationObserver` escuta o evento com `@EventListener` e registra a operacao. O servico de CRUD nao depende diretamente do registro de auditoria.

```java
private void publish(CrudOperation operation, Long resourceId) {
    eventPublisher.publishEvent(new CrudOperationEvent("car", operation, resourceId));
}

@EventListener
public void onOperation(CrudOperationEvent event) {
    operationLog.register(event);
}
```

### Resultado alcancado e mudanca de requisito

Nao houve requisito novo. As operacoes que ja existiam continuam com o mesmo resultado, mas passaram a produzir um registro interno desacoplado. Outros observadores podem ser adicionados no futuro sem mudar os servicos de cliente ou veiculo.

## 5. Singleton

### O que e e para que serve

Singleton garante que uma classe tenha uma unica instancia e fornece um ponto de acesso a ela. Ele e adequado para recursos compartilhados que precisam manter um estado unico durante a execucao da aplicacao.

### Onde, quando e como foi usado

`CrudOperationLog` e um componente Spring com escopo singleton. O observador usa essa mesma instancia para guardar todos os eventos recebidos durante a execucao da aplicacao.

```java
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CrudOperationLog {
    private final List<CrudOperationEvent> operations = new CopyOnWriteArrayList<>();

    public void register(CrudOperationEvent event) {
        operations.add(event);
    }
}
```

### Resultado alcancado e mudanca de requisito

Nao houve requisito novo. O registro interno de operacoes possui uma unica fonte de dados compartilhada pelos observadores, evitando registros separados para cada objeto que precise consultar ou adicionar uma operacao.

## 6. Testes e resultados

Foram adicionados testes para as fabricas, para os decoradores com dados validos e invalidos, para a reacao do observador, para os eventos publicados pelos servicos e para a instancia singleton do Spring. Os testes MVC existentes do CRUD de veiculos tambem foram ajustados para verificar que o `POST /cars` continua compativel.

Comando executado:

```bash
./mvnw test -Djava.version=21
```

Resultado: 14 testes executados, sem falhas, erros ou testes ignorados.

## 7. Conclusao

Os quatro padroes foram incorporados a funcionalidades que ja existiam no projeto. Factory Method organizou a criacao de entidades, Decorator separou a validacao da persistencia, Observer desacoplou o registro das operacoes CRUD e Singleton centralizou esse registro. A API e o banco de dados foram mantidos sem novos requisitos de negocio.
