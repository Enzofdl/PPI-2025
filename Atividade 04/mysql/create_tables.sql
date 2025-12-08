CREATE DATABASE teste;

-- 1) Tabela de usuários do sistema
CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    email VARCHAR(100),
    telefone VARCHAR(20)
);

-- 2) Tabela de categorias dos eventos
CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    descricao VARCHAR(200)
);

-- 3) Tabela de eventos
CREATE TABLE evento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    local_evento VARCHAR(100),
    data_evento DATE,
    id_categoria INT,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id)
);

-- 4) Tabela de inscrições dos participantes
CREATE TABLE inscricao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_evento INT,
    data_inscricao DATE,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    FOREIGN KEY (id_evento) REFERENCES evento(id)
);