SET search_path TO springproject;

CREATE TABLE crudtable (
    id serial PRIMARY KEY,
    item_name VARCHAR(25),
    item_quantity INT,
     entry_time TIMESTAMP
                       );