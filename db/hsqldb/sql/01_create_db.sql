create table pages (
  id INTEGER NOT NULL identity,
  signature VARCHAR_IGNORECASE(20) NOT NULL,
  user_id INTEGER, 
  active_page integer NOT NULL,
  front_page integer NOT NULL,
  title VARCHAR_IGNORECASE(1000),
  content VARCHAR_IGNORECASE,
  timestamp timestamp,
  PRIMARY KEY (id));

create table users (
  id INTEGER NOT NULL identity,
  name VARCHAR_IGNORECASE(50) not null,
  password varchar(30),
  timestamp timestamp,
  UNIQUE(name), 
  PRIMARY KEY(id));

create table navigations (
  id INTEGER NOT NULL identity,
  name VARCHAR_IGNORECASE(50) not null,
  content VARCHAR_IGNORECASE(1000),
  UNIQUE(name), PRIMARY KEY(id));
  