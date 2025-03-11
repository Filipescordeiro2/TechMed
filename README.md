# MVP - TechMed

## Descrição do projeto
TechMed é  gestor de clínicas médicas, oferecendo funcionalidades para agendamento de consultas, gerenciamento de pacientes, médicos e prontuários eletrônicos.

## Tecnologias usadas
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![SQL Database](https://img.shields.io/badge/SQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white)

## Arquitetura usada
A aplicação segue a arquitetura em camadas, permitindo escalabilidade e manutenção facilitada. Utiliza Spring Boot para a criação dos serviços RESTful.

## Tipo de Usuários
- **Administradores**: Gerenciam a clínica, médicos e pacientes.
- **Médicos**: Acessam e atualizam prontuários, gerenciam consultas.
- **Pacientes**: Agendam consultas e visualizam seus históricos.

##  Principais Funções
- 📅 Agendamento de consultas
- 👥 Gerenciamento de pacientes
- 👨‍⚕️ Gerenciamento de médicos
- 📋 Prontuário eletrônico
- 🔔 Notificações e lembretes

##  Dependências usadas
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- Spring Boot Starter Web
- Spring Boot DevTools
- MySQL
- Lombok
- Spring Boot Starter Test

##  Estrutura e responsabilidades

### `controller`
Responsável por gerenciar as requisições HTTP e direcioná-las para os serviços apropriados. Contém as classes que definem os endpoints da API REST.

### `dto`
Contém os Data Transfer Objects, que são usados para transferir dados entre as camadas da aplicação, especialmente entre a camada de apresentação e a camada de serviço.

### `entity`
Contém as entidades JPA que representam as tabelas do banco de dados. Cada classe nesta camada é mapeada para uma tabela específica no banco de dados.

### `Enum`
Contém as enumerações usadas na aplicação para definir conjuntos de constantes, como status de agendamento, tipos de usuários, etc.

### `exception`
Contém as classes de exceção personalizadas usadas para tratar erros específicos da aplicação, como validações de negócio e erros de banco de dados.

### `repository`
Contém as interfaces que estendem `JpaRepository` ou `CrudRepository` do Spring Data JPA, responsáveis por realizar operações de CRUD no banco de dados.

### `service`
Contém as classes de serviço que implementam a lógica de negócio da aplicação. Essas classes são chamadas pelos controladores para processar as requisições e interagir com os repositórios.

## Criado por

**Filipe Santana Cordeiro**  
**Backend Developer**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/filipesantanacordeiro/)
[![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Filipescordeiro2)
