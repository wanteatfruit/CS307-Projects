
create table client
(
    client_id         serial primary key,
    client_enterprise varchar not null unique,
    country           varchar not null,
    city              varchar,
    supply_center     varchar not null,
    industry          varchar
);

create table salesman
(
    salesman_id     serial primary key,
    salesman_number char(8)     not null unique,
    salesman_name   varchar     not null,
    gender          varchar     not null,
    age             integer     not null,
    mobile_phone    varchar(11) not null
);

create table product
(
    product_id   serial primary key,
    product_code varchar(7) not null unique,
    product_name varchar    not null
);

create table model
(
    product_model varchar not null primary key,
    unit_price    integer not null
);

create table contract
(
    contract_id     serial primary key,
    contract_number char(10) not null unique,
    contract_date   date     not null,
    director        varchar  not null
);

create table order_sale
(
    order_id        serial primary key,
    contract_number char(10) not null,
    product_model   varchar  not null,
    est_date        date     not null,
    lodge_date      date,
    quantity        integer  not null,
    unique (contract_number, product_model)

);

alter table contract
    add column client_id integer;
alter table contract
    add constraint client_fk foreign key (client_id) references client (id);

alter table model
    add column product_id integer;
alter table model
    add constraint product_fk foreign key (product_id) references product (product_id);

alter table order_sale
    add constraint model_fk foreign key (product_model) references model (product_model);
alter table order_sale
    add constraint contract_fk foreign key (contract_number) references contract (contract_number);

alter table order_sale
    add column salesman_id integer;
alter table order_sale
    add constraint sm_fk foreign key (salesman_id) references salesman (salesman_id);
