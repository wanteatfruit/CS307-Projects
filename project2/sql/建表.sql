create table center
(
    id   serial
        primary key,
    name varchar not null
        unique
);
create table model
(
    id             serial
        primary key,
    product_number varchar(7) not null,
    product_model  varchar    not null
        unique,
    product_name   varchar    not null,
    unit_price     integer    not null
);
create table enterprise
(
    id              integer not null
        primary key,
    enterprise_name varchar not null
        unique,
    country         varchar not null,
    city            varchar,
    supply_center   varchar not null
        constraint supply_center_fk1
            references center (name),
    industry        varchar
);
create table staff
(
    id            serial
        primary key,
    staff_name    varchar     not null,
    age           integer     not null,
    gender        varchar     not null,
    staff_number  char(8)     not null
        unique,
    supply_center varchar     not null
        constraint supply_center_fk2
            references center (name),
    mobile_number varchar(11) not null,
    type          varchar
);
create table contract
(
    id               integer not null
        primary key,
    contract_num     char(10),
    enterprise       varchar,
    product_model    varchar,
    quantity         integer,
    contract_manager char(8),
    contract_date    date,
    est_date         date,
    lod_date         date,
    salesman_num     char(8),
    contract_type    varchar
);
create table contract_record
(
    contract_num char(10),
    enterprise   varchar,
    manager      char(8)
);
create table storage
(
    id            serial
        primary key,
    supply_center varchar not null
        constraint supply_center_fk
            references center (name),
    product_model varchar not null
        constraint product_model_fk
            references model (product_model),
    quantity      integer not null,
    unique (supply_center, product_model)
);
create table store_record
(
    id             serial
        primary key,
    supply_center  varchar not null
        constraint supply_center_fk
            references center (name),
    product_model  varchar not null
        constraint product_model_fk
            references model (product_model),
    supply_staff   varchar not null
        constraint supply_staff_fk
            references staff (staff_number),
    storage_date   date,
    purchase_price integer,
    quantity       integer,
    constraint storage_supply_center_product_model_storage_date_key
        unique (supply_center, product_model, storage_date)
);

