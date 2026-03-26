USE sistemas_avaliacao;

CREATE TABLE usuario (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         status CHAR(1) NOT NULL,
                         perfil CHAR(1) NOT NULL,
                         created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (id),
                         UNIQUE KEY uk_usuario_email (email)
);