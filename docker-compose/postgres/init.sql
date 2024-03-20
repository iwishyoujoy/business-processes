create table if not exists public.t_role
(
    id   bigint not null
        primary key,
    name varchar(255)
);

alter table public.t_role
    owner to admin;

insert into t_role(id, name) values (0, 'ROLE_AUTHOR'), (1, 'ROLE_READER');