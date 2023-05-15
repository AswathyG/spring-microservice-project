SET search_path TO springproject;

ALTER TABLE crudtable
RENAME COLUMN entry_time TO created_On;