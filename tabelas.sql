CREATE DATABASE sistema_escolar;
USE sistema_escolar;

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL -- O ideal é salvar a senha criptografada (ex: MD5, SHA-256 ou BCrypt)
);

-- Inserindo um usuário de teste (Senha: 123456)
INSERT INTO usuario (nome, login, senha) 
VALUES ('Administrador', 'admin', '123456');

CREATE DATABASE IF NOT EXISTS sistema_escolar;
USE sistema_escolar;

CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);
