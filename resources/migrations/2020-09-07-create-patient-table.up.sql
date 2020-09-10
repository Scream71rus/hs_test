DO $$
BEGIN
    create type gender_type as enum(
        'MALE',
        'FEMALE'
    );
    EXCEPTION
        WHEN duplicate_object THEN null;
END
$$;
--;;
create table if not exists patient(
    id serial primary key,

    first_name text not null,
    middle_name text,
    last_name text not null,

    gender gender_type not null,
    birthday timestamp not null,
    address text,
    medical_insurance text not null,

    created timestamp not null default now()
);
