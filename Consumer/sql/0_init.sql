-- Table: public.user

-- DROP TABLE IF EXISTS public."user";

CREATE TABLE IF NOT EXISTS "user"
(
    id serial NOT NULL,
    create_at timestamp without time zone,
    full_name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    update_at timestamp without time zone,
    username character varying(255) COLLATE pg_catalog."default",
    role character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT uk_sb8bbouer5wak8vyiiy4pf2bx UNIQUE (username)
    );


-- Table: public.device

-- DROP TABLE IF EXISTS public.device;

CREATE TABLE IF NOT EXISTS device
(
    id serial NOT NULL,
    address character varying(255) COLLATE pg_catalog."default",
    created_at timestamp without time zone,
    description character varying(255) COLLATE pg_catalog."default",
    max_consumption_hourly double precision NOT NULL,
    updated_at timestamp without time zone,
    user_id bigint,
    CONSTRAINT device_pkey PRIMARY KEY (id),
    CONSTRAINT fkifs6w44704w8d833btla84u6n FOREIGN KEY (user_id)
    REFERENCES public."user" (id) MATCH SIMPLE
                         ON UPDATE NO ACTION
                         ON DELETE NO ACTION
    );

-- Table: public.backlog

-- DROP TABLE IF EXISTS public.backlog;

CREATE TABLE IF NOT EXISTS backlog
(
    id serial NOT NULL,
    device_id bigint NOT NULL,
    CONSTRAINT backlog_pkey PRIMARY KEY (id),
    CONSTRAINT fkij2tjlhetts0tompgql2h4x1a FOREIGN KEY (device_id)
    REFERENCES public.device (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );


-- Table: public.consumption

-- DROP TABLE IF EXISTS public.consumption;

CREATE TABLE IF NOT EXISTS consumption
(
    id serial NOT NULL,
    consumption_value double precision NOT NULL,
    date timestamp without time zone NOT NULL,
    end_hour integer NOT NULL,
    start_hour integer NOT NULL,
    backlog_id bigint NOT NULL,
    CONSTRAINT consumption_pkey PRIMARY KEY (id),
    CONSTRAINT fktc2hl665i8hgxl4wes87xmqjv FOREIGN KEY (backlog_id)
    REFERENCES public.backlog (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT consumption_end_hour_check CHECK (end_hour >= 1 AND end_hour <= 24),
    CONSTRAINT consumption_start_hour_check CHECK (start_hour >= 0 AND start_hour <= 23)
    );
