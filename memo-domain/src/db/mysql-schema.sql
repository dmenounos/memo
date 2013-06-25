# create user 'memo'@'localhost' identified by 'qweasd';
# grant all on memo.* to 'memo'@'localhost';

drop database if exists memo;
create database memo;
use memo;

create table memo_node (
--	AbstractEntity
	id                  int not null auto_increment primary key,
--	AuditableEntity
	createDate          datetime,
	createUser_id       int,
	updateDate          datetime,
	updateUser_id       int,
--	Resource
	code                varchar(255) not null,
	hint                varchar(255),
	leaf                bit not null,
	hidden              bit not null,
--	Node
	parentNode_id       int
) engine=InnoDB;

create table memo_node_rule (
--	AbstractEntity
	id                  int not null auto_increment primary key,
--	Permissions
	permissions         int not null,
--	NodeRule
	pos                 int not null,
	node_id             int not null,
	userRole_id         int not null
) engine=InnoDB;

create table memo_post (
--	AbstractEntity
	id                  int not null auto_increment primary key,
--	AuditableEntity
	createDate          datetime,
	createUser_id       int,
	updateDate          datetime,
	updateUser_id       int,
--	Resource
	code                varchar(255) not null,
	hint                varchar(255),
	leaf                bit not null,
	hidden              bit not null,
--	Post
	parentNode_id       int
) engine=InnoDB;

create table memo_post_rule (
--	AbstractEntity
	id                  int not null auto_increment primary key,
--	Permissions
	permissions         int not null,
--	PostRule
	pos                 int not null,
	post_id             int not null,
	userRole_id         int not null
) engine=InnoDB;

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

create table memo_openid (
--	AbstractEntity
	id                  int not null auto_increment primary key,
--	OpenID
	user_id             int not null,
	address             varchar(255) not null
) engine=InnoDB;

create table memo_user (
--	AbstractEntity
	id                  int not null auto_increment primary key,
--	User
	email               varchar(255) not null,
	nickname            varchar(255) not null,
	password            varchar(255) not null,
	fullname            varchar(255),
	gender              varchar(255),
	birthday            date,
	country_id          int,
	language_id         int,
	postcode            varchar(255),
	timezone            varchar(255),
	signUpTime          datetime,
	signInTime          datetime
) engine=InnoDB;

create table memo_user_role (
--	AbstractEntity
	id                  int not null auto_increment primary key,
--	UserGroup
	name                varchar(255) not null
) engine=InnoDB;

create table memo_user_role_assoc (
	user_id             int not null,
	role_id             int not null
) engine=InnoDB;

create table memo_audits (
	entityClass         varchar(255) not null,
	entityId            int not null,
	createUser          varchar(255),
	createDate          datetime,
	updateUser          varchar(255),
	updateDate          datetime,
    primary key (entityClass, entityId)
) engine=InnoDB;

alter table memo_node add index       (createUser_id);
alter table memo_node add foreign key (createUser_id) references memo_user(id) on delete set null;
alter table memo_node add index       (updateUser_id);
alter table memo_node add foreign key (updateUser_id) references memo_user(id) on delete set null;
alter table memo_node add index       (parentNode_id);
alter table memo_node add foreign key (parentNode_id) references memo_node(id) on delete cascade;
alter table memo_node add unique key  (parentNode_id, code);

alter table memo_node_rule add index       (node_id);
alter table memo_node_rule add foreign key (node_id) references memo_node(id) on delete cascade;
alter table memo_node_rule add index       (userRole_id);
alter table memo_node_rule add foreign key (userRole_id) references memo_user_role(id) on delete cascade;
alter table memo_node_rule add unique key  (node_id, userRole_id);
alter table memo_node_rule add unique key  (node_id, pos);

alter table memo_post add index       (createUser_id);
alter table memo_post add foreign key (createUser_id) references memo_user(id) on delete set null;
alter table memo_post add index       (updateUser_id);
alter table memo_post add foreign key (updateUser_id) references memo_user(id) on delete set null;
alter table memo_post add index       (parentNode_id);
alter table memo_post add foreign key (parentNode_id) references memo_node(id) on delete cascade;
alter table memo_post add unique key  (parentNode_id, code);

alter table memo_post_rule add index       (post_id);
alter table memo_post_rule add foreign key (post_id) references memo_post(id) on delete cascade;
alter table memo_post_rule add index       (userRole_id);
alter table memo_post_rule add foreign key (userRole_id) references memo_user_role(id) on delete cascade;
alter table memo_post_rule add unique key  (post_id, userRole_id);
alter table memo_post_rule add unique key  (post_id, pos);

alter table memo_openid add index       (user_id);
alter table memo_openid add foreign key (user_id) references memo_user(id) on delete cascade;
alter table memo_openid add unique key  (address);

alter table memo_user add index       (country_id);
alter table memo_user add foreign key (country_id) references memo_country(id);
alter table memo_user add index       (language_id);
alter table memo_user add foreign key (language_id) references memo_language(id);

alter table memo_user_role_assoc add index       (user_id);
alter table memo_user_role_assoc add foreign key (user_id) references memo_user(id) on delete cascade;
alter table memo_user_role_assoc add index       (role_id);
alter table memo_user_role_assoc add foreign key (role_id) references memo_user_role(id) on delete cascade;
alter table memo_user_role_assoc add unique key  (user_id, role_id);
