-- V4__Criar_Tabelas_Treinamento_E_Vinculo.sql

-- 1. Criação da tabela de treinamentos
CREATE TABLE treinamento (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             nome VARCHAR(255) NOT NULL,
                             descricao TEXT,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Criação da tabela intermediária (Muitos-para-Muitos)
-- Esta tabela conecta o Suporte ao Treinamento e armazena a avaliação
CREATE TABLE suporte_treinamento (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     suporte_id BIGINT NOT NULL,
                                     treinamento_id BIGINT NOT NULL,
                                     nota INT NULL CHECK (nota >= 1 AND nota <= 5),
                                     comentario TEXT,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Chaves Estrangeiras para integridade referencial
                                     CONSTRAINT fk_suporte_treinamento_suporte
                                         FOREIGN KEY (suporte_id) REFERENCES suporte(id) ON DELETE CASCADE,

                                     CONSTRAINT fk_suporte_treinamento_treinamento
                                         FOREIGN KEY (treinamento_id) REFERENCES treinamento(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;