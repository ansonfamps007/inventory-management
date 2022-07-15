drop table if exists ITEM_CATEGORY;

create table ITEM_CATEGORY (
  --id int auto_increment  primary key,
  ITEM_CATEGOARY varchar(250) not null,
  MIN_PRIZE double precision not null,
  MAX_PRIZE double precision not null
);