# create user 'memo'@'localhost' identified by 'qweasd';
# grant all on memo.* to 'memo'@'localhost';

drop database if exists memo;
create database memo;
use memo;

create table memo_country (
--	AbstractEntity
	id                  int not null auto_increment primary key,
--	Country
	code                varchar(255) not null,
	name                varchar(255) not null
) engine=InnoDB;

create table memo_language (
--	AbstractEntity
	id                  int not null auto_increment primary key,
--	Language
	code                varchar(255) not null,
	name                varchar(255) not null
) engine=InnoDB;
