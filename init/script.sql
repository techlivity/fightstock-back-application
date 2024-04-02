CREATE TABLE tb_logo (
    id INT PRIMARY KEY AUTO_INCREMENT,
    imagem_url VARCHAR(255)
);

CREATE TABLE tb_nav_bar (
    id INT PRIMARY KEY AUTO_INCREMENT,
    label VARCHAR(255),
    url VARCHAR(255)
);

CREATE TABLE tb_categoria_produtos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome_categoria VARCHAR(255),
    image_categoria VARCHAR(255),
    url_categoria VARCHAR(255),
    descricao_categoria VARCHAR(255)
);

CREATE TABLE tb_produtos_em_estoque (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255),
    image VARCHAR(255),
    descricao VARCHAR(255),
    em_destaque BOOLEAN,
    em_promocao BOOLEAN,
    categoria_id INT,
    FOREIGN KEY (categoria_id) REFERENCES tb_categoria_produtos(id)
);

CREATE TABLE tb_carrossel (
    id INT PRIMARY KEY AUTO_INCREMENT,
    imagem VARCHAR(255),
    url VARCHAR(255)
);

CREATE TABLE endereco (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cep VARCHAR(10),
    estado VARCHAR(255),
    cidade VARCHAR(255),
    logradouro VARCHAR(255),
    numero VARCHAR(10),
    bairro VARCHAR(255),
    complemento VARCHAR(255)
);

CREATE TABLE contato (
    id INT PRIMARY KEY AUTO_INCREMENT,
    telefone_fixo VARCHAR(20),
    telefone_celular VARCHAR(20),
    telefone_ouvidoria VARCHAR(20),
    email VARCHAR(255),
    endereco_id INT,
    FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);

CREATE TABLE redirecionadores (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255),
    url VARCHAR(255)
);
