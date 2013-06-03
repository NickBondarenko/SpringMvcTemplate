DROP TABLE state;
DROP TABLE address;
DROP TABLE users;
DROP TABLE groups;
DROP TABLE group_authorities;
DROP TABLE group_members;

CREATE TABLE state (
	id BIGINT IDENTITY PRIMARY KEY,
	abbreviation CHAR (2) NOT NULL,
	name VARCHAR (128) NOT NULL
);

CREATE TABLE address (
	id BIGINT IDENTITY PRIMARY KEY,
	street VARCHAR (256) NOT NULL,
	additional_info VARCHAR (256),
	city VARCHAR (256) NOT NULL,
	state_id BIGINT NOT NULL,
	zip_code_prefix INTEGER NOT NULL,
	zip_code_suffix INTEGER,
	country VARCHAR (256),
	county VARCHAR (256),
	within_city_limits BOOLEAN NOT NULL,
	CONSTRAINT FK_ADDRESS_STATE FOREIGN KEY(state_id) REFERENCES state(id)
);

CREATE TABLE users (
  id BIGINT IDENTITY PRIMARY KEY,
  username VARCHAR(256) NOT NULL UNIQUE,
  password varchar(256) NOT NULL,
	email_address varchar(256) NOT NULL UNIQUE,
  first_name varchar(256) NOT NULL,
  last_name varchar(256) NOT NULL,
	enabled BOOLEAN NOT NULL DEFAULT TRUE,
	account_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
	account_non_locked BOOLEAN  NOT NULL DEFAULT TRUE,
	credentials_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
	address_id BIGINT NOT NULL,
	CONSTRAINT FK_USERS_ADDRESS FOREIGN KEY(address_id) REFERENCES address(id)
);

CREATE TABLE groups (
	id BIGINT IDENTITY PRIMARY KEY,
	group_name VARCHAR(256) NOT NULL
);

CREATE TABLE group_authorities (
	id BIGINT IDENTITY PRIMARY KEY,
	group_id BIGINT NOT NULL,
	authority VARCHAR(256) NOT NULL,
	CONSTRAINT FK_GROUP_AUTHORITIES_GROUP FOREIGN KEY(group_id) REFERENCES groups(id)
);

CREATE TABLE group_members (
	id BIGINT IDENTITY PRIMARY KEY,
	user_id BIGINT NOT NULL,
	group_id BIGINT NOT NULL,
	CONSTRAINT FK_GROUP_MEMBERS_GROUP FOREIGN KEY(group_id) REFERENCES groups(id),
	CONSTRAINT FK_GROUP_MEMBERS_USER FOREIGN KEY(user_id) REFERENCES users(id)
);