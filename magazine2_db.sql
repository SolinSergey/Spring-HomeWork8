CREATE DATABASE magazine2_db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

---------
	
CREATE TABLE IF NOT EXISTS public.product
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    title character varying(100) COLLATE pg_catalog."default" NOT NULL,
    price integer NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT product_title_key UNIQUE (title)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.product
    OWNER to postgres;

---------

INSERT INTO public.product (title,price) VALUES
('Сыр', 250),
('Селедка', 500),
('Лимонад', 55),
('Молоко', 59),
('Кефир', 60),
('Огурчики', 100),
('Хлеб', 35),
('Сметана', 85),
('Оленина', 2500),
('Йогурт', 55),
('Мандарины', 200),
('Макароны', 70),
('Гречка', 95),
('Квас', 75),
('Сливки', 100),
('Пончики', 65),
('Конфеты', 600),
('Зефир', 150),
('Мармелад', 500),
('Капуста', 5),
('Крекер', 54),
('Манка', 75),
('Булгур', 95),
('Сахар', 107),
('Свинина', 350);

---------

