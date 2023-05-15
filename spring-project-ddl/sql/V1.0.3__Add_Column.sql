SET search_path TO springproject;

ALTER TABLE crudtable
ADD COLUMN last_updated_on TIMESTAMP;