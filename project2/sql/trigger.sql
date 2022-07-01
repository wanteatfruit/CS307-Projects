--trigger
create or replace function ins_check()
    returns trigger
as
$$
declare
    ex_id    int;
    cur_id   int;
    ex_name  varchar;
    cur_name varchar;
begin
    cur_name := new.name;
    cur_id := new.id;
    select name into ex_name from center where name = cur_name;
    select id into ex_id from center where id = cur_id;

    if cur_name = ex_name or cur_id = ex_id then
        return null;
    end if;
    return new;
end;
$$ language plpgsql;

create or replace function ent_check()
    returns trigger
as
$$
declare
    ex_id    int;
    cur_id   int;
    ex_name  varchar;
    cur_name varchar;
    cur_fk   varchar;
    cur_ref  varchar;
begin
    cur_name := new.enterprise_name;
    cur_id := new.id;
    cur_fk = new.supply_center;
    raise notice '%',cur_fk;
    raise notice '%',cur_ref;
    select enterprise_name into ex_name from enterprise where enterprise_name = cur_name;
    select id into ex_id from enterprise where id = cur_id;
    select name into cur_ref from center where name = cur_fk;
    raise notice '%',cur_fk;
    raise notice '%',cur_ref;

    if cur_name = ex_name or cur_id = ex_id or cur_ref is null then
        return null;
    end if;
    return new;
end;
$$ language plpgsql;

create or replace function model_check()
    returns trigger
as
$$
declare
    ex_id    int;
    cur_id   int;
    ex_name  varchar;
    cur_name varchar;


begin
    cur_name := new.product_model;
    cur_id := new.id;

    select product_model into ex_name from model where product_model = cur_name;
    select id into ex_id from model where id = cur_id;

    if cur_name = ex_name or cur_id = ex_id then
        return null;
    end if;
    return new;
end;
$$ language plpgsql;

create or replace function staff_check()
    returns trigger
as
$$
declare
    cur_id   int;
    ex_id    int;
    ex_name  varchar;
    cur_name varchar;
        cur_fk1  varchar;
    cur_ref1 varchar;
begin
    cur_name := new.staff_number;
    cur_id = new.id;
    cur_fk1:=new.supply_center;
    select staff_number into ex_name from staff where staff_number = cur_name;
    select id into ex_id from enterprise where id = cur_id;
    select name into cur_ref1 from center where name=cur_fk1;
    if cur_name = ex_name or cur_id = ex_id or cur_ref1 is null then
        return null;
    end if;
    return new;
end;
$$ language plpgsql;





create trigger ins_trigger
    before insert
    on center
    for each row
execute procedure ins_check();

create trigger ent_trigger
    before insert
    on enterprise
    for each row
execute procedure ent_check();


create trigger mod_trigger
    before insert
    on model
    for each row
execute procedure model_check();
create trigger staff_trigger
    before insert
    on staff
    for each row
execute procedure staff_check();

-- trigger test
insert into center values (1,'test'); -- won't be inserted, dup primary key
insert into center values (1000,'Asia'); -- won't be inserted, dup unique column
insert into enterprise values (100000,'test','test','test','test','test'); -- won't be inserted, no fk reference