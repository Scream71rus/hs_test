create schema if not exists hs_test;

create type hs_test.gender_type as enum(
    'MALE',
    'FEMALE'
);

create table if not exists hs_test.patient(
    id serial primary key,

    first_name text not null,
    middle_name text,
    last_name text not null,

    gender hs_test.gender_type not null,
    birthday timestamp not null,
    address text,
    medical_insurance text not null,

    created timestamp not null default now()
);
