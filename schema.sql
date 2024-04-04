create table carts
(
    cart_id bigserial not null,
    primary key (cart_id)
);

create table carts_products
(
    cart_cart_id        bigint not null,
    products_product_id bigint not null unique
);

create table customers
(
    account_enable      boolean,
    account_expired     boolean,
    account_locked      boolean,
    credentials_expired boolean,
    cart_id             bigint unique,
    customer_id         bigserial    not null,
    email               varchar(255) not null unique,
    password            varchar(255) not null,
    primary key (customer_id)
);

create table products
(
    price              numeric(38, 2),
    quantity_available integer,
    product_id         bigserial not null,
    title              varchar(255),
    primary key (product_id)
);

create table roles
(
    customer_id bigint    not null,
    role_id     bigserial not null,
    role        varchar(255) check (role in ('ADMIN', 'ANONYMOUS', 'USER')),
    primary key (role_id)
);

alter table if exists carts_products
    add constraint FKkjflqlmg69rknq1i79uvaj265
        foreign key (products_product_id)
            references products;

alter table if exists carts_products
    add constraint FK9hfmdik2aam15udyoqdf4819
        foreign key (cart_cart_id)
            references carts;

alter table if exists customers
    add constraint FKihj385ysmggb5xuqydq8nb33e
        foreign key (cart_id)
            references carts;

alter table if exists roles
    add constraint role_customer_fk
        foreign key (customer_id)
            references customers;
