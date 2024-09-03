# Chestplace: Arquitetura
Por:
- Carlos Krefer
- Gabriel Martins
- Guilherme Martins
- Gustavo Aymoto
- Stella Spricigo

# Introdução e objetivos    

O Projeto Chestplace é um software que:
- Permite a divulgação de vendas de camisetas
- Permite a compra de camisetas


## Contextualização
Tendo em vista que, de maneira geral, no mercado de camisetas, há uma grande segmentação (entre o mercado de camisetas novas e usadas, entre vendedores de pequeno ou grande porte, entre diferentes marcas), este software irá permitir a sua centralização, ou seja, tornará possível a venda de camisetas novas e usadas em um único espaço, tanto por vendedores individuais como por empresas. Além disso, foi percebido que é prática comum exigir o cadastro para a realização de compras. Esse requerimento pode, em alguns casos, encarecer a experiência do usuário, devido ao tempo gasto para o preenchimento dos formulários. Dessa maneira, o nosso produto de software tornará opcional o cadastro para a realização da compra. 

## Overview dos Requisitos
O sistema deverá permitir a compra e venda de camisetas novas e usadas, além do gerenciamento de vendas e compras. Será possível que os usuários gerenciem seus próprios perfis, cadastrem novos usuários e autentiquem os já cadastrados. O sistema também deverá oferecer opções de pagamento por cartão de crédito, débito e Pix.

### _Principais funcionalidades_
- Cadastrar anúncios de camisetas
- Comprar camisetas
- Gerenciar vendas realizadas
- Gerenciar compras realizadas

## Objetivos de Qualidade

| Nr. |       Qualidade        |                                                                                Motivação                                                                                 |
| :-: | :--------------------: | :----------------------------------------------------------------------------------------------------------------------------------------------------------------------: |
|  1  |    Disponibilidade     |                       Como a fonte de renda é a venda de produtos, é necessário estar sempre disponível para que os clientes comprem os produtos.                        |
|  2  |     Escalabilidade     |                Por ser uma startup em crescimento, é necessário ser escalável para suportar uma quantidade crescente de usuários conforme a necessidade.                 |
|  3  |   Confidencialidade    | Dado que um e-commerce trata de dados pessoais sensível (CPF, e-mail, endereço, número de cartão de crédito, CEP) um vazamento poderia causar um prejuízo significativo. |
|  4  | Engajamento de Usuário |                                               Se o cliente ficar e voltar ao site, ele irá comprar mais e dar mais renda.                                                |


## Stakeholders
A lista a seguir contém os stakeholders do sistema.


|   Nome    |                                 Objetivos/Limites                                  |
| :-------: | :--------------------------------------------------------------------------------: |
| Comprador | Ter que acessar diversos sites de camisetas para encontrar um modelo de seu gosto. |
| Vendedor  |                       Divulgar o seu empreendimento online.                        |


# Restrições Arquiteturais

## Restrições técnicas

|  ID  |                   Restrição                    |                                                         Motivação                                                         |
| :--: | :--------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------------: |
| RT01 |             Implementação backend              |                           A aplicação deve utlizar Azure Functions para criação de controllers.                           |
| RT02 | Implementação frontend em JavaScript (Angular) |                                      A aplicação deve utlizar JavaScript e Angular.                                       |
| RT03 | A aplicação deve suportar diferentes browsers  | A aplicação deverá ser compatível com os browsers Google Chrome, Microsoft Edge, Firefox, Opera, Opera GX, Safari e Brave |
| RT04 |        Integração com sistemas externos        |                 A aplicação deverá ser integrada com o sistema do ViaCep, Firebase e sistema de pagamento                 |

## Restrições Organizacionais

|  ID  |                   Restrição                   |                                     Motivação                                      |
| :--: | :-------------------------------------------: | :--------------------------------------------------------------------------------: |
| RO01 |                     Time                      | Carlos Krefer, Gabriel Martins, Guilherme Martins, Gustavo Aymoto, Stella Spricigo |
| RO02 |                IDE do Projeto                 |                      Será usado o VSCode para desenvolvimento                      |
| RO03 | Configuração e Gerenciamento de versionamento |            O versionamento será feito por meio de repositório no GitHub            |
| RO04 |                  Cronograma                   |                      A data final do projeto será 15/nov/2024                      |

## Convenções
### _Lista de Convençoes_
| ID  |          Restrição          |                                     Motivação                                      |
| :-: | :-------------------------: | :--------------------------------------------------------------------------------: |
| C1  |           Idioma            | Português. A documentação e implementação do projeto será totalmente em Português. |
| C2  | Documentação da Arquitetura |              Será utilizado o modelo em Português do ARC42 e C4 Model              |

# Escopo e Contexto do Sistema
## Contexto Técnico
**Diagrama Nível I**

    C4Context
        title Marketplace de camisetas

        Person(usuario, "Usuário", "Usuário comprador ou vendedor de camisetas.")
        System(marketplace, "Marketplace de camisetas", "Plataforma para vender e comprar camisetas.")

        BiRel(usuario, marketplace, "Usa")

        System_Ext(apiCEP, "ViaCEP", "API do sistema que retorna informações sobre o CEP fornecido.")

        System_Ext(sistemaFinanceiro, "Sistema Financeiro", "API do sistema financeiro.")

        System_Ext(firebase, "Firebase", "Sistema de autenticação.")

        BiRel(marketplace, apiCEP, "Usa")
        BiRel(marketplace, sistemaFinanceiro, "Usa")
        BiRel(marketplace, firebase, "Usa")

**Diagrama Nível II**
     
    C4Context
        title Marketplace de camisetas

        Person(usuario, "Usuário", "[Pessoa]<br>Usuário comprador ou vendedor de camisetas.")
        System(marketplace, "Marketplace de camisetas", "[Angular]<br>Frontend da plataforma para vender e comprar camisetas.")

        Boundary(back, "Sistema backend") {
            System(apiAuth, "API Autenticação", "[Java]<br>Microserviço para autenticação.")
            System(apiGateway, "API Gateway", "[Azure API Management]<br>Roteamento para serviços.")
            System(apiMarketplace, "API Marketplace", "[Java]<br>Microserviço para as<br>principais funcionalidades do marketplace.")
            System(apiCarrinho, "API Carrinho", "[Java]<br>Microserviço para<br>carrinho de compras.")
            SystemDb(bancoMongo, "Banco de dados<br>não relacional", "[MongoDB]<br>Armazena<br>dados de carrinhos.")
            SystemDb(bancoSQL, "Banco de dados<br>relacional", "[SQL Server]<br>Armazena<br>dados do marketplace.")
        }

        System_Ext(apiCEP, "ViaCEP", "[Sistema de software]<br>API do sistema que retorna informações sobre o CEP fornecido.")
        System_Ext(firebase, "Firebase", "[Sistema de software]<br>Sistema de autenticação.")
        System_Ext(sistemaFinanceiro, "Sistema Financeiro", "[Sistema de software]<br>API do sistema financeiro.")

        BiRel(usuario, marketplace, "Usa")
        BiRel(marketplace, apiGateway, "Usa", "HTTP/JSON")
        BiRel(marketplace, apiCEP, "Usa", "HTTP/JSON")
        BiRel(apiGateway, apiAuth, "Usa", "HTTP/JSON")
        BiRel(apiGateway, apiCarrinho, "Usa", "HTTP/JSON")
        BiRel(apiGateway, apiMarketplace, "Usa", "HTTP/JSON")
        BiRel(apiAuth, firebase, "Usa", "HTTP/JSON")
        BiRel(apiCarrinho, bancoMongo, "Usa", "<br>MongoDB<br>Wire<br>Protocol")
        BiRel(apiMarketplace, bancoSQL, "Usa", "TDS")
        BiRel(apiMarketplace, sistemaFinanceiro, "Usa", "HTTP/JSON")

        UpdateRelStyle(apiAuth, firebase, $offsetY="-80", $offsetX="-20")
        UpdateRelStyle(apiGateway, apiAuth, $offsetY="-40", $offsetX="-30")
        UpdateRelStyle(apiGateway, apiMarketplace, $offsetY="-40", $offsetX="-30")
        UpdateRelStyle(apiMarketplace, bancoSQL, $offsetY="0", $offsetX="10")
        UpdateRelStyle(apiCarrinho, bancoMongo, $offsetY="-50", $offsetX="-20")
        UpdateRelStyle(apiGateway, apiCarrinho, $offsetY="-20", $offsetX="-60")
        UpdateRelStyle(marketplace, apiGateway, $offsetY="-180", $offsetX="60")
        UpdateRelStyle(marketplace, apiCEP, $offsetY="0", $offsetX="-80")
        UpdateRelStyle(usuario, marketplace, $offsetY="-10", $offsetX="0")
        UpdateLayoutConfig($c4ShapeInRow="3", $c4BoundaryInRow="1")

### _Backend_

O sistema e o banco de dados estão disponiblizados em um servidor no Azure.

O sistema se comunica com a API do ViaCep por meio de requisições GET para consultar dados de endereço.

Há também a utilização de uma API de pagamentos para realização de ações financeiros dentro do sistema.

Além disso, há a utlização do Firebase para autenticação de usuários no sistema.


### _Frontend_

O frontend será desenvolvido em Angular, sendo disponibilizado em como um sistema Web, se comunicando com o usuário via requisições HTTP.

