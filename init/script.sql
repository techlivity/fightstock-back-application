CREATE TABLE logo (
    id INT PRIMARY KEY,
    imagem_url VARCHAR(255)
);

CREATE TABLE nav_bar (
    id INT PRIMARY KEY,
    label VARCHAR(255),
    url VARCHAR(255),
    parent_id INT,
    FOREIGN KEY (parent_id) REFERENCES nav_bar(id)
);

CREATE TABLE categoria_produtos (
    id INT PRIMARY KEY,
    nome VARCHAR(255),
    image VARCHAR(255),
    url VARCHAR(255),
    descricao TEXT,
    em_promocao BOOLEAN
);

CREATE TABLE produtos_em_estoque (
    id INT PRIMARY KEY,
    nome VARCHAR(255),
    image VARCHAR(255),
    descricao_texto TEXT,
    descricao_tamanho VARCHAR(255),
    descricao_dimensao VARCHAR(255),
    descricao_peso VARCHAR(255),
    descricao_marca VARCHAR(255),
    redirect_url VARCHAR(255),
    em_destaque BOOLEAN,
    em_promocao BOOLEAN
);

CREATE TABLE carrossel (
    id INT PRIMARY KEY,
    imagem VARCHAR(255),
    url VARCHAR(255)
);

CREATE TABLE footer_contato (
    id INT PRIMARY KEY,
    telefone_fixo VARCHAR(20),
    telefone_celular VARCHAR(20),
    telefone_ouvidoria VARCHAR(20),
    email VARCHAR(255),
    endereco_cep VARCHAR(10),
    endereco_estado VARCHAR(255),
    endereco_cidade VARCHAR(255),
    endereco_logradouro VARCHAR(255),
    endereco_numero VARCHAR(10),
    endereco_bairro VARCHAR(255),
    endereco_complemento VARCHAR(255)
);

CREATE TABLE redirecionadores (
    id INT PRIMARY KEY,
    nome VARCHAR(255),
    url VARCHAR(255)
);
