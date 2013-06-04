INSERT INTO state(ABBREVIATION, NAME) VALUES ('AL', 'Alabama');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('AK', 'Alaska');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('AS', 'American Samoa');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('AZ', 'Arizona');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('AR', 'Arkansas');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('CA', 'California');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('CO', 'Colorado');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('CT', 'Connecticut');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('DE', 'Delaware');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('DC', 'District of Columbia');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('FM', 'Federated States of Micronesia');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('FL', 'Florida');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('GA', 'Georgia');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('GU', 'Guam');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('HI', 'Hawaii');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('ID', 'Idaho');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('IL', 'Illinois');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('IN', 'Indiana');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('IA', 'Iowa');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('KS', 'Kansas');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('KY', 'Kentucky');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('LA', 'Louisiana');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('ME', 'Maine');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('MH', 'Marshall Islands');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('MD', 'Maryland');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('MA', 'Massachusetts');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('MI', 'Michigan');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('MN', 'Minnesota');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('MS', 'Mississippi');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('MO', 'Missouri');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('MT', 'Montana');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('NE', 'Nebraska');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('NV', 'Nevada');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('NH', 'New Hampshire');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('NJ', 'New Jersey');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('NM', 'New Mexico');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('NY', 'New York');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('NC', 'North Carolina');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('ND', 'North Dakota');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('MP', 'North Mariana Islands');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('OH', 'Ohio');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('OK', 'Oklahoma');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('OR', 'Oregon');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('PW', 'Palau');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('PA', 'Pennsylvania');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('PR', 'Puerto Rico');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('RI', 'Rhode Island');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('SC', 'South Carolina');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('SD', 'South Dakota');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('TN', 'Tennessee');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('TX', 'Texas');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('UT', 'Utah');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('VT', 'Vermont');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('VI', 'Virgin Islands');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('VA', 'Virginia');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('WA', 'Washington');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('WV', 'West Virginia');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('WI', 'Wisconsin');
INSERT INTO state(ABBREVIATION, NAME) VALUES ('WY', 'Wyoming');

INSERT INTO country(code, name) VALUES ('US', 'United States');
INSERT INTO country(code, name) VALUES ('CN', 'Canada');

INSERT INTO address(street, additional_info, city, state_id, zip_code_prefix, zip_code_suffix, country_id, county, within_city_limits) VALUES ('11562 8th St N APT 708', '', 'Saint Petersburg', 12, '33716', '0', 1, 'Pineallas', TRUE);

INSERT INTO users(username, password, email_address, first_name, last_name, enabled, account_non_expired, account_non_locked, credentials_non_expired, address_id) VALUES ('user1@example.com','38aab7ba97bd6bb2e51add1e5617eabfc8d13ec85c004e909eec4b70172437ae85e0c56e43fe51b0','user1@example.com','User', '1', true, true, true, true, 1);
INSERT INTO users(username, password, email_address, first_name, last_name, enabled, account_non_expired, account_non_locked, credentials_non_expired, address_id) VALUES ('admin1@example.com','98afcd6f54569da7fea7fe4b1bf79d59dd27e559d38ee75cabd796f43058ebe15f201dfd453942e0','admin1@example.com','Admin', '1', true, true, true, true, 1);
INSERT INTO users(username, password, email_address, first_name, last_name, enabled, account_non_expired, account_non_locked, credentials_non_expired, address_id) VALUES ('user2@example.com','429d7af2097fb1a0a3a4050bff17d8189cb2244aef52476cad2fef3bcc7338078dbf73644494554a','user2@example.com','User', '2', true, true, true, true, 1);

INSERT INTO groups(group_name) VALUES ('Users');
INSERT INTO groups(group_name) VALUES ('Administrators');

INSERT INTO group_authorities(group_id, authority) SELECT id, 'ROLE_USER' FROM groups WHERE group_name = 'Users';
INSERT INTO group_authorities(group_id, authority) SELECT id, 'ROLE_USER' FROM groups WHERE group_name = 'Administrators';
INSERT INTO group_authorities(group_id, authority) SELECT id, 'ROLE_ADMIN' FROM groups WHERE group_name = 'Administrators';

INSERT INTO group_members(group_id, user_id) SELECT id, (SELECT id FROM users WHERE username = 'user1@example.com') FROM groups WHERE group_name = 'Users';
INSERT INTO group_members(group_id, user_id) SELECT id, (SELECT id FROM users WHERE username = 'admin1@example.com') FROM groups WHERE group_name = 'Administrators';
INSERT INTO group_members(group_id, user_id) SELECT id, (SELECT id FROM users WHERE username = 'user2@example.com') FROM groups WHERE group_name = 'Users';