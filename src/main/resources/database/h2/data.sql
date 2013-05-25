INSERT INTO address(street, additional_info, city, state, zip_code_prefix, zip_code_suffix, country, county, within_city_limits) VALUES ('11562 8th St N APT 708', '', 'Saint Petersburg', 'FL', 33716, 0, 'United States', 'Pineallas', TRUE);

insert into users(id,username,password,email_address,first_name,last_name, address_id) values (0,'user1@example.com','user1','user1@example.com','User','1', 0);
insert into users(id,username,password,email_address,first_name,last_name, address_id) values (1,'admin1@example.com','admin1','admin1@example.com','Admin','1', 0);
insert into users(id,username,password,email_address,first_name,last_name, address_id) values (2,'user2@example.com','user2','user2@example.com','User','2', 0);

-- insert into events (id,when,summary,description,owner,attendee) values (100,'2013-10-04 20:30:00','Birthday Party','This is going to be a great birthday',0,1);
-- insert into events (id,when,summary,description,owner,attendee) values (101,'2013-12-23 13:00:00','Conference Call','Call with the client',2,0);
-- insert into events (id,when,summary,description,owner,attendee) values (102,'2014-01-23 11:30:00','Lunch','Eating lunch together',1,2);