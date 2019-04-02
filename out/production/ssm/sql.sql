
create database charset=utf8;

create table city(
	id int not null primary key auto_increment,
	name varchar(100),
	privince varchar(50)
)engine=innodb charset=utf8;

create table blog(
	id int not null primary key auto_increment,
	title varchar(200),
	brief varchar(400),
	author varchar(100),
	content text,
	read_count int default 0,
	href varchar(200),
	addon datetime default now(),
	flag_delete tinyint default 0
)engine=innodb charset=utf8;

create table request_log(
	id int not null primary key auto_increment,
	url varchar(200),
	data varchar(2000),
	elapsed_time int,
	addon datetime default now()
)engine=innodb charset=utf8;