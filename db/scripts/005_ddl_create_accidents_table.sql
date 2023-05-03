CREATE TABLE if not exists accidents (
    id serial primary key,
    name text,
	description text,
	address text,
	accident_type_id int references accident_type(id)
);