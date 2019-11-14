# Sistemas de venda de ingressos para cinema :ticket:

## Descrição do projeto :pencil2:
o trabalho consistiu na criação de um sistema para venda de insgressos para um cinema usando uma ferramenta de automatização
de testes.

## Tecnologias utilizadas :bookmark:

### para a aplicação Web:
  - Spring MVC
  - Spring Data
  - Banco de dados H2
  - Bootstrap
### para a realização dos Testes:
  - Junit 5
  - Mockito
  
## Casos de teste :page_with_curl:

- os seguintes casos de testes foram elaborados:
  - CT1 - Cadastrar filme
  - CT2 - Salvar Filme
  - CT3 - Cadastrar Sessão
  - CT4 - Salvar Sessão
  - CT5 - Cadastrar Filme com informações incorretas(vazias)
  - CT6 - Cadastrar Sessão com informações incorretas(vazias)
  - CT7 - Cadastrar filme com data 7 dias após a data atual
  - CT8 - Cadastrar filme com data de estreia diferente de quinta
  - Ct9 - Cadastrar Sessão(Filme) com horário de exibição diferente do horário permitido(12:00 - 22:00)
  - CT10 - Cadastrar Sessão(Filme) com diferença dos horários menor do que a duração do filme
  - CT11 - Cadastrar Sessão(Filme) em uma sala com filme no mesmo horário
  - CT12 - Vender Ticket para assento ocupado
  - CT13 - Vender Ticket  para assentos de maneira que fique 1 assento isolado entre dois assentos ocupados
  - CT14 - Vender Ticket para um horário de exibição onde todos os Tickets já foram vendidos

## Executando o projeto :rocket:

### Clone o repositorio

execute `git clone https://github.com/marlo2222/CinemaV.git`
`cd CinemaV`
 
### Executando e instalando depencias do projeto
execute `mvn package` para baixar as dependecias do projeto. `mvn depedency:tree` para listar as dependencias instaladas.
use ` mvn spring-boot:run` para executar sua aplicação.
:warning: OBS :warning: Caso não tenha o comando `mvn` em sua maquina utilize o seguinte [tutorial](https://www.hostinger.com.br/tutoriais/install-maven-ubuntu/) :thumbsup:.

## Colaboradores :busts_in_silhouette:

### este projeto foi realizado juntamento com os colegas: 
- [Sunana Moreira](https://github.com/SusanaMCosta) :octocat:
- [Cleiton Monteiro](https://github.com/cleitonmonteiro) :octocat:
- [Artur Castro](https://github.com/ArturCRS) :octocat:


