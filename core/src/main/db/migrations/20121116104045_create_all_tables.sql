-- Table: default_text

-- DROP TABLE default_text;

CREATE TABLE default_text
(
  id bigint NOT NULL,
  text text NOT NULL,
  lastupdated timestamp without time zone NOT NULL DEFAULT now(),
  CONSTRAINT default_text_pkey PRIMARY KEY (id )
);

-- Table: point_of_sale

-- DROP TABLE point_of_sale;

CREATE TABLE point_of_sale
(
  id bigint NOT NULL,
  name character varying(255) NOT NULL,
  lastupdated timestamp without time zone NOT NULL DEFAULT now(),
  inuse boolean DEFAULT false,
  CONSTRAINT point_of_sale_pkey PRIMARY KEY (id )
);

-- Table: category

-- DROP TABLE category;

CREATE TABLE category
(
  id bigint NOT NULL,
  name bigint NOT NULL DEFAULT 0,
  description bigint NOT NULL DEFAULT 0,
  lastupdated timestamp without time zone NOT NULL DEFAULT now(),
  inuse boolean DEFAULT false,
  CONSTRAINT category_pkey PRIMARY KEY (id ),
  CONSTRAINT category_description_id_fkey FOREIGN KEY (description)
      REFERENCES default_text (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT category_name_id_fkey FOREIGN KEY (name)
      REFERENCES default_text (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table: category_tree

-- DROP TABLE category_tree;

CREATE TABLE category_tree
(
  parent bigint NOT NULL,
  child bigint NOT NULL,
  lastupdated timestamp without time zone NOT NULL DEFAULT now(),
  CONSTRAINT category_tree_pkey PRIMARY KEY (child , parent ),
  CONSTRAINT fkcfb3e63faee9a105 FOREIGN KEY (parent)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkcfb3e63fefe28d77 FOREIGN KEY (child)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table: point_of_sale_category

-- DROP TABLE point_of_sale_category;

CREATE TABLE point_of_sale_category
(
  id bigint NOT NULL,
  pointofsale bigint NOT NULL,
  category bigint NOT NULL,
  pointofsaleorder integer,
  lastupdated timestamp without time zone NOT NULL DEFAULT now(),
  CONSTRAINT point_of_sale_category_pk PRIMARY KEY (id ),
  CONSTRAINT category_fk FOREIGN KEY (category)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT point_of_sale_fk FOREIGN KEY (pointofsale)
      REFERENCES point_of_sale (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT pointofsale_category_unique UNIQUE (pointofsale , category )
);

--
-- Name: default_text_sequence; Type: SEQUENCE; Schema: public;
--

CREATE SEQUENCE default_text_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: sku_category_sequence; Type: SEQUENCE; Schema: public;
--

CREATE SEQUENCE category_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: point_of_sale_sequence; Type: SEQUENCE; Schema: public;
--

CREATE SEQUENCE point_of_sale_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: point_of_sale_category_sequence; Type: SEQUENCE; Schema: public;
--

CREATE SEQUENCE POINT_OF_SALE_CATEGORY_SEQUENCE
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
