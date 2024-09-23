--liquibase formatted sql

--changeset goose:emitents
CREATE TABLE IF NOT EXISTS emitents(
    "id"    BIGINT PRIMARY KEY,
    "bin"   VARCHAR(255)    NOT NULL,
    "min_range" VARCHAR(19) NOT NULL,
    "max_range" VARCHAR(19) NOT NULL,
    "alpha_code"    VARCHAR(2),
    "bank_name" VARCHAR(255)
);