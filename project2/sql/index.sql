--index
create index on model(product_name);
create index on enterprise(industry);
create index on staff(staff_name);
create index on storage(product_model);
create index on storage(supply_center);
create index on store_record(product_model);
create index on contract_record(contract_num);
create index on contract(contract_num);
create index on contract(product_model);
create index on contract(salesman_num);

--index test
-- only applies to large enough table
explain analyse select * from model where product_model='MobilePhoneCompanion86'; -- single index scan
explain analyse select * from contract where salesman_num='12312131' and contract_num='CSE0000106'; -- two indexes
create index staff_staff_name_idx on staff(staff_name);
explain analyse select * from staff where staff_name  ='Kong Yibo';
drop index staff_staff_name_idx;