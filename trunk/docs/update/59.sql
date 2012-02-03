alter table purchase add column total_amount decimal(10,2) default 0.00;
alter table purchase add column ship_no varchar(32) default '';