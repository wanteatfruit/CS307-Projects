select * from contracts_project.pg_catalog.pg_roles;
--原本是上海
select * from order_sale;
update order_sale set lodge_date = '2018-03-13' where order_id = 1;
update client set supply_center = 'ShangHai' where client_id = 1;
insert into salesman values(991,'21112221','zhang','Male',26,13945620153);
delete from salesman where salesman_id = 991;


create role person_manager with LOGIN;
alter role person_manager with password '654321';
create role director with LOGIN;
alter role director with password 'cs307';
create role visitor with LOGIN;
alter role visitor with LOGIN;
alter role visitor with password '123';
create role company with LOGIN ;
alter role company with password '456';
create role test with password '123456';
select * from information_schema.table_privileges where grantee='director';
alter user checker with CREATEROLE ;
alter role director with NOCREATEDB ;
alter role director with NOCREATEROLE ;
alter role person_manager with NOCREATEDB ;
alter role person_manager with NOCREATEROLE ;

grant update on client to company;
grant insert on client to company;
grant delete on client to company;

grant update on product to company;
grant insert on product to company;
grant insert on product to company;

grant update on model to company;
grant insert on model to company;
grant insert on model to company;

grant update on contract to company;
grant insert on contract to company;
grant insert on contract to company;

grant update on order_sale to company;
grant insert on order_sale to company;
grant insert on order_sale to company;
--均可访问
grant select on client to public ;
grant select on contract to public ;
grant select on order_sale to public ;
grant select on product to public ;
grant select on salesman to public ;
grant select on model to public ;
--
grant update on salesman to person_manager;
grant insert on salesman to person_manager;
grant delete on salesman to person_manager;

grant update on contract to director;
grant insert on contract to director;
grant delete on contract to director;

grant update on order_sale to director;
grant insert on order_sale to director;
grant insert on order_sale to director;