-- V5__Adicionar_Mes_Referencia.sql
ALTER TABLE suporte_treinamento
    ADD COLUMN mes_referencia VARCHAR(7) NOT NULL AFTER comentario;

-- Adiciona um índice para buscas rápidas por mês (opcional, mas recomendado)
CREATE INDEX idx_mes_referencia ON suporte_treinamento(mes_referencia);