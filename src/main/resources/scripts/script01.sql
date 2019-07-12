--Table
drop table roles if exists;
drop table user_roles if exists;
drop table users if exists;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;
create table roles (id bigint generated by default as identity, name varchar(60), primary key (id));
create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id));
create table users (id bigint not null, active boolean, email varchar(40), name varchar(40), password varchar(100), primary key (id));
alter table roles add constraint UK_nb4h0p6txrmfc0xbrd1kglp9t unique (name);
alter table users add constraint UK_sx468g52bpetvlad2j9y0lptc unique (email);
alter table user_roles add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles;
alter table user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users;

--Dump
insert  into roles values(1,'ADMIN');
insert  into roles values(2,'USER');