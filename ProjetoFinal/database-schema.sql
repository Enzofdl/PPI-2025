-- Script SQL para criar banco e tabelas
-- Plataforma de Eventos

CREATE DATABASE IF NOT EXISTS Plataforma_de_Eventos;
USE Plataforma_de_Eventos;

-- Tabela de Categorias
CREATE TABLE IF NOT EXISTS categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT
);

-- Tabela de Usuários
CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone VARCHAR(20)
);

-- Tabela de Eventos
CREATE TABLE IF NOT EXISTS evento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    local_evento VARCHAR(200) NOT NULL,
    data_evento DATE NOT NULL,
    id_categoria INT NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id) ON DELETE CASCADE
);

-- Tabela de Inscrições
CREATE TABLE IF NOT EXISTS inscricao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_evento INT NOT NULL,
    data_inscricao DATE NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_evento) REFERENCES evento(id) ON DELETE CASCADE,
    UNIQUE KEY unique_inscricao (id_usuario, id_evento)
);

-- Dados de exemplo para Categorias
INSERT INTO categoria (nome, descricao) VALUES 
    ('Tecnologia', 'Eventos relacionados a tecnologia e inovação'),
    ('Educação', 'Eventos educacionais e acadêmicos'),
    ('Entretenimento', 'Shows, festas e eventos culturais'),
    ('Esportes', 'Competições e eventos esportivos'),
    ('Negócios', 'Conferências e networking empresarial');

-- Dados de exemplo para Usuários
INSERT INTO usuario (nome, email, telefone) VALUES 
    ('João Silva', 'joao@email.com', '11999999999'),
    ('Maria Santos', 'maria@email.com', '11988888888'),
    ('Pedro Oliveira', 'pedro@email.com', '11977777777');

