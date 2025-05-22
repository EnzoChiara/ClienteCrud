# 📋 ClienteCRUD

![Java](https://img.shields.io/badge/Java-8%2B-blue) ![Swing](https://img.shields.io/badge/Swing-UI-lightgrey) ![MySQL](https://img.shields.io/badge/MySQL-%3E%3D5.7-blue)

## 🌟 Descrição

ClienteCRUD é uma aplicação **desktop** desenvolvida em **Java Swing** que permite gerenciar um cadastro de clientes (Nome, CPF, Telefone e E-mail) com funcionalidades completas de **CRUD** e **filtro de busca**.

---

## 🚀 Funcionalidades

- **Adicionar** novo cliente  
- **Buscar** cliente por CPF  
- **Atualizar** dados de um cliente  
- **Excluir** cliente (com confirmação)  
- **Filtrar** lista de clientes por parte do nome  
- Máscaras e validações em **CPF**, **Telefone** e **E-mail**  
- Confirmação via diálogo (`JOptionPane`) antes de ações críticas  

---

## 📋 Pré-requisitos

- **Java JDK 8** (ou superior)  
- **MySQL Server** (>= 5.7)  
- **IDE Java** (NetBeans, IntelliJ IDEA, Eclipse…)  
- **Maven** (para gerenciar dependências) ou adicionar manualmente o JAR do Connector/J  

---

## 🛠️ Configuração do Banco de Dados

1. Acesse seu MySQL (Workbench, CLI, etc.).  
2. Execute os comandos abaixo:

   ```sql
   CREATE DATABASE cadastro;
   USE cadastro;

   CREATE TABLE clients (
     id    INT AUTO_INCREMENT PRIMARY KEY,
     name  VARCHAR(100) NOT NULL,
     cpf   VARCHAR(14)  NOT NULL UNIQUE,
     phone VARCHAR(15),
     email VARCHAR(100)
   );
