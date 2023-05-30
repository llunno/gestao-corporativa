# Gestão Corporativa

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)

Uma aplicação web utilizando JAVA e Springboot focada em gerenciar colaboradores dentro de uma organização, fortemente baseada na hierarquia organizacional. A aplicação contém, além de métodos de CRUD, um módulo de solução de conflitos entre funcionários baseado no grau hierárquico dos mesmos, bem como no grau hierárquico de seus superiores diretos, indicando o melhor mediador possível para os conflitos.

### Instalação

Para rodar a aplicação em desenvolvimento em sua máquina, garanta que seu computador possua o Java JDK instalado (o projeto utiliza java 17) e faça um clone desta aplicação. Baixe as dependências contidas no arquivo pom.xml caso sua IDE não tenha o feito automaticamente. Altere os dados de conexão com o banco de dados no arquivo application.properties, na pasta resources para conectar com seu banco. Também é recomendado executar as rotinas clean e install do Maven.
