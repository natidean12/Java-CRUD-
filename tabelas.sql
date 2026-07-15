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

CREATE DATABASE IF NOT EXISTS bd_academico;
USE bd_academico;

-- 1. Tabela de Usuários (para o Login e Cadastro)
CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

-- 2. Tabela de Cursos
CREATE TABLE IF NOT EXISTS curso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    duracao INT NOT NULL -- em semestres ou anos
);

-- 3. Tabela de Disciplinas
CREATE TABLE IF NOT EXISTS disciplina (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    carga_horaria INT NOT NULL
);

-- 4. Tabela de Alunos
CREATE TABLE IF NOT EXISTS aluno (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    matricula VARCHAR(20) NOT NULL UNIQUE,
    data_nascimento DATE
);

-- 5. Tabela de Professores
CREATE TABLE IF NOT EXISTS professor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    especialidade VARCHAR(100)
);

-- ==========================================================
-- TABELAS DE ASSOCIAÇÃO (MUITOS PARA MUITOS)
-- ==========================================================

-- Associação: Curso x Professor (Quais professores ensinam em quais cursos)
CREATE TABLE IF NOT EXISTS curso_professor (
    curso_id INT,
    professor_id INT,
    PRIMARY KEY (curso_id, professor_id),
    FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE,
    FOREIGN KEY (professor_id) REFERENCES professor(id) ON DELETE CASCADE
);

-- Associação: Curso x Disciplina (Quais disciplinas pertencem a quais cursos)
CREATE TABLE IF NOT EXISTS curso_disciplina (
    curso_id INT,
    disciplina_id INT,
    PRIMARY KEY (curso_id, disciplina_id),
    FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE,
    FOREIGN KEY (disciplina_id) REFERENCES disciplina(id) ON DELETE CASCADE
);

-- Associação: Professor x Disciplina (Quais disciplinas o professor ministra)
CREATE TABLE IF NOT EXISTS professor_disciplina (
    professor_id INT,
    disciplina_id INT,
    PRIMARY KEY (professor_id, disciplina_id),
    FOREIGN KEY (professor_id) REFERENCES professor(id) ON DELETE CASCADE,
    FOREIGN KEY (disciplina_id) REFERENCES disciplina(id) ON DELETE CASCADE
);

-- Associação: Aluno x Disciplina (Matrícula do aluno nas disciplinas para gerar notas/gráficos)
CREATE TABLE IF NOT EXISTS aluno_disciplina (
    aluno_id INT,
    disciplina_id INT,
    PRIMARY KEY (aluno_id, disciplina_id),
    FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON DELETE CASCADE,
    FOREIGN KEY (disciplina_id) REFERENCES disciplina(id) ON DELETE CASCADE
);
