create table users (
  id BIGINT IDENTITY,
  username VARCHAR(256) NOT NULL UNIQUE,
  password varchar(256) NOT NULL,
	email_address varchar(256) NOT NULL UNIQUE,
  first_name varchar(256) NOT NULL,
  last_name varchar(256) NOT NULL,
	address_id BIGINT
);

CREATE TABLE state (
	abbreviation CHAR (2) PRIMARY KEY,
	description VARCHAR (64) NOT NULL
);

CREATE TABLE address (
	id BIGINT IDENTITY,
	street VARCHAR (256) NOT NULL,
	additional_info VARCHAR (256),
	city VARCHAR (256) NOT NULL,
	state CHAR (2) NOT NULL,
	zip_code_prefix INTEGER NOT NULL,
	zip_code_suffix INTEGER,
	country VARCHAR (256),
	county VARCHAR (256),
	within_city_limits BOOLEAN NOT NULL,
)

-- create table events (
--     id bigint identity,
--     when timestamp not null,
--     summary varchar(256) not null,
--     description varchar(500) not null,
--     owner bigint not null,
--     attendee bigint not null,
--     FOREIGN KEY(owner) REFERENCES users(id),
--     FOREIGN KEY(attendee) REFERENCES users(id)
-- );