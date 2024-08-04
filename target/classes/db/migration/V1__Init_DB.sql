create table if not exists result(
                       id bigserial primary key,
                       ip_address varchar,
                       input varchar,
                       answer varchar
);