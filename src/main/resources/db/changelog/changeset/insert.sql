--changeset RuslanYuneev:insert data
--comment Insert data migration

insert into roles (id, name)
values (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');
