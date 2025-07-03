# 💳 Bank Account Management Application

Cette application permet de gérer des **comptes bancaires** pour des clients, avec la possibilité d'effectuer des **opérations de débit et de crédit**. Elle supporte deux types de comptes : **courants** et **épargnes**, et est divisée en trois parties : Backend (Spring Boot), Frontend (Angular), et Sécurité (Spring Security + JWT).

---

## 🧱 Technologies utilisées

- **Backend** : Java 17, Spring Boot 3, Spring Data JPA, H2/MySQL, Swagger, Spring Security, JWT
- **Frontend** : Angular
- **Authentification** : Spring Security + JSON Web Token (JWT)

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
