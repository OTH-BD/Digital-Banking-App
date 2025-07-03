# 💳 Bank Account Management Application

Cette application permet de gérer des **comptes bancaires** pour des clients, avec la possibilité d'effectuer des **opérations de débit et de crédit**. Elle supporte deux types de comptes : **courants** et **épargnes**, et est divisée en trois parties : Backend (Spring Boot), Frontend (Angular), et Sécurité (Spring Security + JWT).

---


## 🧰 Technologies utilisées

| Couche         | Technologies principales                      |
|----------------|-----------------------------------------------|
| Backend        | Spring Boot 3, Spring Data JPA, Lombok ...    |
| Base de données| MySQL (via WAMP Server)                       |
| Frontend       | Angular , Bootstrap , CSS                     |
| Sécurité       | Spring Security, JSON Web Token (JWT)         |
| Documentation  | Swagger (springdoc-openapi)  v3               |
| Autres outils  | Maven, Postman, Git, IntelliJ / VSCode        |

---


## 📂 Structure du projet

```
ebanking/
│
├── ebanking-backend/
│   └── src/main/java/net/bd/ebankingbackend/
│       ├── dtos/
│       ├── entities/
│       ├── enums/
│       ├── exceptions/
│       ├── mappers/
│       ├── repositories/
│       ├── services/
│       └── web/

│
├── ebanking-frontend/
│   └── angular-bank-app/
│       ├── components/
│       └── services/
│
└── README.md
```
