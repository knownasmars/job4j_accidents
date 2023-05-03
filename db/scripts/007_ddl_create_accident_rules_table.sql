CREATE TABLE if not exists accident_rules (
 id serial primary key,
	accident_id int references accidents(id),
	rule_id int references rule(id)
);