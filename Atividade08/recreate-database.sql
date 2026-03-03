-- Script para recriar banco de dados do zero
-- Execute: mysql -u root -p < recreate-database.sql

DROP DATABASE IF EXISTS Plataforma_de_Eventos;
CREATE DATABASE Plataforma_de_Eventos;
USE Plataforma_de_Eventos;

-- Tabela de Categorias
CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT
);

-- Tabela de Usuários
CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone VARCHAR(20)
);

-- Tabela de Eventos
CREATE TABLE evento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    local_evento VARCHAR(200) NOT NULL,
    data_evento DATE NOT NULL,
    id_categoria INT NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id) ON DELETE CASCADE
);

-- Tabela de Inscrições
CREATE TABLE inscricao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_evento INT NOT NULL,
    data_inscricao DATE NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_evento) REFERENCES evento(id) ON DELETE CASCADE,
    UNIQUE KEY unique_inscricao (id_usuario, id_evento)
);

-- ============================================
-- DADOS DE EXEMPLO
-- ============================================

-- Categorias
INSERT INTO categoria (nome, descricao) VALUES 
    ('Tecnologia', 'Eventos relacionados a tecnologia e inovação'),
    ('Educação', 'Eventos educacionais e acadêmicos'),
    ('Entretenimento', 'Shows, festas e eventos culturais'),
    ('Esportes', 'Competições e eventos esportivos'),
    ('Negócios', 'Conferências e networking empresarial');

-- Usuários
INSERT INTO usuario (nome, email, telefone) VALUES 
    ('João Silva', 'joao@email.com', '(11) 99999-9999'),
    ('Maria Santos', 'maria@email.com', '(11) 98888-8888'),
    ('Pedro Oliveira', 'pedro@email.com', '(11) 97777-7777'),
    ('Ana Costa', 'ana@email.com', '(11) 96666-6666');

-- Eventos
INSERT INTO evento (nome, local_evento, data_evento, id_categoria) VALUES 
    ('Tech Conference 2025', 'Centro de Convenções SP', '2025-06-15', 1),
    ('Workshop de Java', 'Auditório FIAP', '2025-05-20', 1),
    ('Feira de Ciências', 'Escola Estadual', '2025-07-10', 2),
    ('Show de Rock', 'Estádio do Morumbi', '2025-08-25', 3),
    ('Maratona SP', 'Ibirapuera', '2025-09-10', 4);

-- Inscrições
INSERT INTO inscricao (id_usuario, id_evento, data_inscricao) VALUES 
    (1, 1, '2025-01-15'),
    (1, 2, '2025-01-16'),
    (2, 1, '2025-01-17'),
    (3, 3, '2025-01-18'),
    (4, 4, '2025-01-19');

-- ============================================
-- VERIFICAÇÃO
-- ============================================

SELECT 'Banco criado com sucesso!' as Status;
SELECT COUNT(*) as Total_Categorias FROM categoria;
SELECT COUNT(*) as Total_Usuarios FROM usuario;
SELECT COUNT(*) as Total_Eventos FROM evento;
SELECT COUNT(*) as Total_Inscricoes FROM inscricao;

