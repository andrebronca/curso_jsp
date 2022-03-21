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
)
----------------------------------------------------------------------------
