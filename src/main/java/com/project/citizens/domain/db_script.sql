-- SEQUENCE: public.persons_id_seq

CREATE SEQUENCE public.persons_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.persons_id_seq
    OWNER TO test_user;

-- SEQUENCE: public.docs_id_seq

CREATE SEQUENCE public.docs_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.docs_id_seq
    OWNER TO test_user;


- Table: public.persons

-- Table: public.persons

CREATE TABLE public.persons
(
    id bigint NOT NULL DEFAULT nextval('persons_id_seq'::regclass),
    birth_date date NOT NULL,
    first_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    location character varying(255) COLLATE pg_catalog."default" NOT NULL,
    middle_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    sex character varying(10) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT persons_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.persons
    OWNER to test_user;


-- Table: public.docs

CREATE TABLE public.docs
(
    id bigint NOT NULL DEFAULT nextval('docs_id_seq'::regclass),
    begin_date date NOT NULL,
    doc_number character varying(100) COLLATE pg_catalog."default" NOT NULL,
    doc_type character varying(255) COLLATE pg_catalog."default" NOT NULL,
    end_date date NOT NULL,
    org_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    person_id bigint NOT NULL,
    CONSTRAINT docs_pkey PRIMARY KEY (id),
    CONSTRAINT fk7i9404niundgcroj21yahqtim FOREIGN KEY (person_id)
        REFERENCES public.persons (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.docs
    OWNER to test_user;