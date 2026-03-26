CREATE TABLE usuario_token (
                               id          BIGINT          NOT NULL AUTO_INCREMENT,
                               token       VARCHAR(255)    NOT NULL UNIQUE,
                               usuario_id  BIGINT          NOT NULL,
                               tipo        VARCHAR(50)     NOT NULL,
                               expiracao   DATETIME        NOT NULL,
                               created_at  DATETIME        DEFAULT CURRENT_TIMESTAMP,

                               PRIMARY KEY (id),
                               CONSTRAINT fk_usuario_token_usuario
                                   FOREIGN KEY (usuario_id)
                                       REFERENCES usuario(id)
                                       ON DELETE CASCADE
);