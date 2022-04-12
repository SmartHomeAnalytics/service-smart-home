create table if not exists test_test
(
    "id"   int4 PRIMARY KEY,
    "name" text NOT NULL DEFAULT false
);

insert into test_test
values (1, 'test 1'),
       (2, 'test 2');
