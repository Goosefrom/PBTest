--liquibase formatted sql

--changeset goose:sequence
CREATE SEQUENCE IF NOT EXISTS emt_sequence INCREMENT BY 1000 MINVALUE 1 CYCLE;