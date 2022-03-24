-- Database: cursojsp

-- DROP DATABASE cursojsp;

CREATE DATABASE cursojsp
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Portuguese_Brazil.1252'
       LC_CTYPE = 'Portuguese_Brazil.1252'
       CONNECTION LIMIT = -1;

COMMENT ON DATABASE cursojsp
  IS 'aula 23.16 jdev';
 
----------------------------------------------------------------------------
CREATE TABLE public.model_login (
 id serial primary key,
 nome character varying(255) not null,
 email character varying(255) not null,
 login character varying(255) unique NOT NULL,
 senha character varying(255) NOT NULL
);

--Definir qual usuário terá o perfil administrador
alter table model_login add column useradmin boolean not null default false;

alter table model_login add column usuario_id integer;

alter table model_login alter column set usuario_id not null;
alter table model_login alter column set usuario_id default 2; --nesse caso 2 é o id do admin

alter table model_login add constraint usuario_id_fk foreign key (usuario_id) references model_login(id);
----------------------------------------------------------------------------
