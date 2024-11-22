--changeset RuslanYuneev:init
--comment Init migration

create table users (
    id bigserial primary key,
    email varchar(30) not null unique,
    password varchar(80) not null,
    first_name varchar(100) not null,
    second_name varchar(100) not null
);

create table roles (
    id serial primary key,
    name varchar(50) not null unique
);

create table users_roles (
    user_id bigint not null references users(id),
    role_id bigint not null references roles(id),
    primary key (user_id, role_id)
);

create type status as enum (
  'CREATED',
  'PROCESSING',
  'COMPLETED'
);

create type priority as enum (
  'LOW',
  'MEDIUM',
  'HIGH'
);

create table tasks (
    id bigserial primary key,
    title varchar(50) not null,
    description text not null,
    current_status status not null,
    current_priority priority not null,
    creator_id bigint not null references users(id),
    performer_id bigint references users(id)
);

create table comments (
    id bigserial primary key,
    task_id bigint not null references tasks(id),
    user_id bigint not null references users(id),
    content text not null,
    created_at timestamp not null
);