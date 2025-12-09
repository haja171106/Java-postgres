create database product_management_db;

create user product_manager_user with password '123456';

grant connect on database product_management_db to product_manager_user;

grant usage on schema public to product_manager_user;

grant select, insert, update, delete on all tables in schema public to product_manager_user;
grant all privileges on all sequences in schema public to product_manager_user;

grant create on schema public to product_manager_user;

alter default privileges in schema public
grant select, insert, update, delete on tables to product_manager_user;

alter default privileges in schema public
grant all privileges on sequences to product_manager_user;
