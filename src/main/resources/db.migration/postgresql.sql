DROP TABLE IF EXISTS "endereco" CASCADE;
DROP TABLE IF EXISTS "usuario" CASCADE;
DROP TABLE IF EXISTS "imovel" CASCADE;
DROP TABLE IF EXISTS "caracteristica_imovel" CASCADE;
DROP TABLE IF EXISTS "anuncio" CASCADE;
DROP TABLE IF EXISTS "anuncio_formas_aceitas" CASCADE;
DROP TABLE IF EXISTS "reserva" CASCADE;

DROP SEQUENCE IF EXISTS "endereco_id_seq";
DROP SEQUENCE IF EXISTS "usuario_id_seq";
DROP SEQUENCE IF EXISTS "imovel_id_seq";
DROP SEQUENCE IF EXISTS "caracteristica_imovel_id_seq";
DROP SEQUENCE IF EXISTS "anuncio_id_seq";
DROP SEQUENCE IF EXISTS "reserva_id_seq";

CREATE SEQUENCE "endereco_id_seq" INCREMENT BY 1 START WITH 1 NO CYCLE;
CREATE SEQUENCE "usuario_id_seq" INCREMENT BY 1 START WITH 1 NO CYCLE;
CREATE SEQUENCE "imovel_id_seq" INCREMENT BY 1 START WITH 1 NO CYCLE;
CREATE SEQUENCE "caracteristica_imovel_id_seq" INCREMENT BY 1 START WITH 1 NO CYCLE;
CREATE SEQUENCE "anuncio_id_seq" INCREMENT BY 1 START WITH 1 NO CYCLE;
CREATE SEQUENCE "reserva_id_seq" INCREMENT BY 1 START WITH 1 NO CYCLE;

CREATE TABLE "endereco"(
    "id" int8 NOT NULL DEFAULT nextval('endereco_id_seq'::regclass),
    "bairro" VARCHAR(255) NOT NULL,
    "cep" VARCHAR(255) NOT NULL,
    "cidade" VARCHAR(255) NOT NULL,
    "complemento" VARCHAR(255),
    "estado" VARCHAR(255) NOT NULL,
    "logradouro" VARCHAR(255) NOT NULL,
    "numero" VARCHAR(255) NOT NULL,
    PRIMARY KEY ("id")
)

CREATE TABLE "usuario"(
    "id" int8 NOT NULL DEFAULT nextval('usuario_id_seq'::regclass),
    "cpf" VARCHAR(255),
    "data_nascimento" DATE NOT NULL,
    "email" VARCHAR(255),
    "foto" VARCHAR(255),
    "nome" VARCHAR(255),
    "senha" VARCHAR(255),
    "id_endereco" int8,
    PRIMARY KEY ("id"),
    CONSTRAINT "fk_usuario_01" FOREIGN KEY ("id_endereco") REFERENCES "endereco" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
)

CREATE TABLE "imovel"(
    "id" int8 NOT NULL DEFAULT nextval('imovel_id_seq'::regclass),
    "deleted" BOOLEAN NOT NULL,
    "identificacao" VARCHAR(255),
    "tipo_imovel" VARCHAR(255),
    "id_endereco" int8,
    "id_proprietario" int8,
    PRIMARY KEY ("id"),
    CONSTRAINT "fk_imovel_01" FOREIGN KEY ("id_endereco") REFERENCES "endereco" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT "fk_imovel_02" FOREIGN KEY ("id_proprietario") REFERENCES "usuario" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
)

CREATE TABLE "caracteristica_imovel"(
    "id" int8 NOT NULL DEFAULT nextval('caracteristica_imovel_id_seq'::regclass),
    "descricao" VARCHAR(255),
    "id_imovel" int8,
    PRIMARY KEY ("id"),
    CONSTRAINT "fk_caracteristica_imovel_01" FOREIGN KEY ("id_imovel") REFERENCES "imovel" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
)

CREATE TABLE "anuncio"(
    "id" int8 NOT NULL DEFAULT nextval('anuncio_id_seq'::regclass),
    "deleted" BOOLEAN NOT NULL,
    "descricao" VARCHAR(255),
    "tipo_anuncio" VARCHAR(255),
    "valor_diaria" DECIMAL(19, 2),
    "id_anunciante" int8,
    "id_imovel" int8,
    PRIMARY KEY ("id"),
    CONSTRAINT "fk_anuncio_01" FOREIGN KEY ("id_anunciante") REFERENCES "usuario" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT "fk_anuncio_02" FOREIGN KEY ("id_imovel") REFERENCES "imovel" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
)

CREATE TABLE "anuncio_formas_aceitas"(
    "anuncio_id" int8 NOT NULL,
    "formas_aceitas" VARCHAR(255)
)

CREATE TABLE "reserva"(
    "id" int8 NOT NULL DEFAULT nextval('reserva_id_seq'::regclass),
    "data_hora_reserva" TIMESTAMP,
    "forma_escolhida" VARCHAR(255),
    "status" VARCHAR(255),
    "valor_total" DECIMAL(19, 2),
    "data_hora_final" TIMESTAMP,
    "data_hora_inicial" TIMESTAMP,
    "quantidade_pessoas" INTEGER,
    "id_anuncio" int8,
    "id_solicitante" int8,
    PRIMARY KEY ("id"),
    CONSTRAINT "fk_reserva_01" FOREIGN KEY ("id_anuncio") REFERENCES "anuncio" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT "fk_reserva_02" FOREIGN KEY ("id_solicitante") REFERENCES "usuario" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
)