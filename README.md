<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&height=250&color=0:030737,60:2426aa&text=DIO%20Lab%20Padrões%20de%20Projeto%20Spring&reversal=false&section=header&textBg=false&animation=fadeIn&fontColor=f8fafc&fontSize=50">

# :question: Sobre

Esse projeto é um exercício da aula da **DIO "Explorando Padrões de Projetos na Pratica com Java"** onde o código fonte
do projeto original se encontra neste [link](https://github.com/digitalinnovationone/lab-padroes-projeto-spring).

O projeto original contempla os padrões **Strategy/Repository, Singleton e Facade** utilizando Spring, e com base no
projeto apresentado resolvi aprimorar.

O projeto atual utiliza:

* Java 21
* Spingboot 3
* Swagger 3
* Projeto Lombook
* H2 Dtabase
* JUnit 5

Temos neste projeto os seguintes Design Patters:
|Creatinal|Sturctural|Behayioural
|--|--|--|
|Singleton|Adapter|Strategy|
|Factory|Decorator| |
|Builder|Facade| |

Trata-se de uma API simples de cadastro de clientes com endereço, com métodos de recuperar todos, recuperar um pelo id,
criar, atualizar e apagar. Os dados para cadastro e atualização são somente nome do cliente, cep e complemento. Temos um
banco com 3 tabelas, uma de cliente, uma de endereço de cliente e outra de endereço, como podemos ver no diagrama
abaixo:

```mermaid
erDiagram
    TB_CLIENTE ||--|| TB_ENDERECO_CLIENTE: tem
    TB_ENDERECO_CLIENTE }|--|| TB_ENDERECO: endereco_base
    TB_CLIENTE {
        Long id
        String nome
        Long endereco_id
    }
    TB_ENDERECO_CLIENTE {
        Long id
        String complemento
        Long endereco_id
    }

    TB_ENDERECO {
        Long id
        String cep
        String logradouro
        String unidade
        String bairro
        String localidade
        String uf
        String ddd
        String ibge
        String gia
        String siafi
    }
```

A ideia de ser ter duas tabelas de endereço é que a tabela de endereço tem seus dados repetidos, por exemplo, em um
condomínio de muitas unidades todas terão a mesma base de endereço porque o cep é o mesmo, mudando somente o bloco e o
apartamento, dados estes que serão armazenados na coluna complemento da tabela tb_endereco_cliente.

A tabela tb_endereco é preenchida automaticamente com base no cep passado sendo feita uma consulta no serviço externo
ViaCep. E na próxima vez que for feito outro cadastro com o mesmo cep o registro é reaproveitado, não sendo necessário
uma nova consulta ao serviço e sem duplicar dados.

Resumidamente a arquitetura de classes está assim:

```mermaid
classDiagram
    class JpaRepository
    <<interface>> JpaRepository

    class ClienteController {
        -ClienteService clientServer
        +buscarTodos()
        +buscarPorId(Lond id)
        +inserir(ClienteRequestDto cliente)
        +atualizar(Long id, ClienteRequestDto cliente)
        +deletar(Lond id)
    }
    class ClienteService {
        buscarTodos()
        buscarPorId(Lond id)
        inserir(ClienteRequestDto cliente)
        atualizar(Long id, ClienteRequestDto cliente)
        deletar(Lond id)
    }
    class EnderecoService {
        +getEnderecoByCep(String cep)
    }
    class ViaCepService {
        +getEnderecoByCep(String cep)
    }
    class ClienteServiceImpl {
        -ClienteRepository clienteRepository
        -EnderecoService enderecoService
        -EnderecoClienteRepository enderecoClienteRepository
        buscarTodos()
        buscarPorId(Lond id)
        inserir(ClienteRequestDto cliente)
        atualizar(Long id, ClienteRequestDto cliente)
        deletar(Lond id)
    }
    class EnderecoServiceImpl {
        -EnderecoRepository enderecoRepository
        -ViaCepService viaCepService
        +getEnderecoByCep(String cep)
    }
    class ViaCepServiceImpl {
        -ViaCepRepository viaCepRepository
        +getEnderecoByCep(String cep)
    }
    class ClienteRepository
    class EnderecoRepository
    class EnderecoClienteRepository
    class ViaCepRepository

    <<interface>> ClienteService
    <<interface>> EnderecoService
    <<interface>> ViaCepService
    <<interface>> ClienteRepository
    <<interface>> EnderecoRepository
    <<interface>> EnderecoClienteRepository
    <<interface>> ViaCepRepository
    JpaRepository <|-- ClienteRepository: extends
    JpaRepository <|-- EnderecoRepository: extends
    JpaRepository <|-- EnderecoClienteRepository: extends
    JpaRepository <|-- ViaCepRepository: extends
    ClienteController *-- ClienteService: composition
    ClienteService <|-- ClienteServiceImpl: implements
    ClienteServiceImpl *-- ClienteRepository
    ClienteServiceImpl *-- EnderecoService
    ClienteServiceImpl *-- EnderecoClienteRepository
    EnderecoService <|-- EnderecoServiceImpl: implements
    EnderecoServiceImpl *-- ViaCepService: composition
    EnderecoServiceImpl *-- EnderecoRepository: composition
    ViaCepService <|-- ViaCepServiceImpl: implements
    ViaCepServiceImpl *-- ViaCepRepository: composition

```

Vamos exemplificar com o diagrama de sequência de inclusão:

```mermaid
sequenceDiagram
    Client ->>+ ClienteController: post(json)
    ClienteController ->>+ ClienteService: inserir(ClienteRequestDto)
    ClienteService ->>+ EnderecoClienteRepository: getReferenceById()
    EnderecoClienteRepository -->>- ClienteService: null
    ClienteService ->>+ EnderecoService: getEnderecoByCep(cep)
    EnderecoService ->>+ EnderecoRepository: findByCep(cep)
    EnderecoRepository -->>- EnderecoService: null
    EnderecoService ->>+ ViaCepService: findByCep(cep)
    ViaCepService -->>- EnderecoService: EnderecoEntity
    EnderecoService ->>+ EnderecoRepository: save(EnderecoEntity)
    EnderecoRepository -->>- EnderecoService: EnderecoEntity
    EnderecoService -->>- ClienteService: EnderecoEntity
    ClienteService ->>+ ClienteRepository: save(ClienteEntity)
    ClienteRepository -->>- ClienteService: EnderecoEntity
    ClienteService -->>- ClienteController: ClienteResponseDto
    ClienteController -->>- Client: json
```

<br/>
<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&height=150&color=0:030737,60:2426aa&reversal=false&section=footer&textBg=false&animation=fadeIn&fontColor=f8fafc">
