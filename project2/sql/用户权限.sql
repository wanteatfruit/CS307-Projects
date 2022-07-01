--权限
create role visitor with LOGIN ;
alter role visitor with password '654321';
create role people_manager with LOGIN ;
alter role people_manager with password '114514';
create role enterprise with LOGIN ;
alter role enterprise with password '1919';
create role model_manager with LOGIN ;
alter role model_manager with password '810';

grant select on model to public ;
grant select on center to public ;
grant select on staff to public ;
grant select on enterprise to public;

grant update on staff to people_manager;
grant delete on staff to people_manager;
grant insert on staff to people_manager;
grant select on contract to people_manager;
grant select on contract_record to people_manager;

grant update on contract_record to enterprise;
grant delete on contract_record to enterprise;
grant insert on contract_record to enterprise;
grant update on contract to enterprise;
grant delete on contract to enterprise;
grant insert on contract to enterprise;
grant select on contract to enterprise;
grant select on contract_record to enterprise;

grant update on model to model_manager;
grant insert on model to model_manager;
grant delete on model to model_manager;
grant update on storage to model_manager;
grant delete on storage to model_manager;
grant insert on storage to model_manager;
grant update on store_record to model_manager;
grant delete on store_record to model_manager;
grant insert on store_record to model_manager;
grant select on store_record to model_manager;
grant select on storage to model_manager;

--test
select * from contract;--1
insert into contract (id, contract_num, enterprise, product_model, quantity, contract_manager,contract_date,est_date,lod_date, salesman_num, contract_type)
values (1001,'CSE0000500','Alibaba','DatabaseSoftwareO6',1,'11711129','2022-05-22','2022-05-26',null,'11310409','Unfinished'); --2
delete from contract where id = 1001;--3