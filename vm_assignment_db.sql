drop database vmdb;
drop user vmuser;
create user vmuser with password 'password';
create database vmdb with template=template0 owner=vmuser;
\connect vmdb;
alter default privileges grant all on tables to vmuser;
alter default privileges grant all on sequences to vmuser;

create table vm_users(
user_id integer primary key not null,
first_name varchar(20) not null,
last_name varchar(20) not null,
email varchar(30) not null,
password text not null,
mobile varchar(20) not null,
role varchar(30) not null
);

create table vm_spec(
vm_id integer primary key not null,
user_id integer not null,
os varchar(50) not null,
ram integer not null,
hd integer not null,
cpu integer not null
);
alter table vm_spec add constraint vm_users_fk
foreign key (user_id) references vm_users(user_id);

create sequence vm_users_seq increment 1 start 1;
create sequence vm_spec_seq increment 1 start 1;















