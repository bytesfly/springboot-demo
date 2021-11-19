create table tb_book
(
    id     bigint primary key,
    name   varchar(32),
    author varchar(20)
);

create table tb_hero
(
    id      bigint primary key,
    name    varchar(32),
    age     int,
    skill   varchar(32),
    bid bigint
);



