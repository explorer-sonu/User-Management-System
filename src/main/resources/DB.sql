insert into country_master values(1, 'India');
insert into country_master values(2, 'USA');

insert into state_master(state_id, state_name, country_id) values(1, 'Bihar',1);
insert into state_master(state_id, state_name, country_id) values(2, 'AP',1);

insert into state_master(state_id, state_name, country_id) values(3, 'RI',2);
insert into state_maste(state_id, state_name, country_id) values(4, 'NJ',2);

insert into city_master(city_id, city_name, state_id) values(1, 'Araria',1);
insert into city_master(city_id, city_name, state_id) values(2, 'Purnea',1);

insert into city_master(city_id, city_name, state_id) values(3, 'Guntur',2);
insert into city_master(city_id, city_name, state_id) values(4, 'Ongole',2);

insert into city_master(city_id, city_name, state_id) values(5, 'providence',3);
insert into city_master(city_id, city_name, state_id) values(6, 'New Port',3);

insert into city_master(city_id, city_name, state_id) values(7, 'Trenton',1);
insert into city_master(city_id, city_name, state_id) values(8, 'Newark',1);

