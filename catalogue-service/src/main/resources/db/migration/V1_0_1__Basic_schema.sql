create schema if not exists catalogue;

create table catalogue.t_product (
    id serial primary key,
    c_name varchar(50) not null,
    c_description varchar(1000),
    c_price float not null
)