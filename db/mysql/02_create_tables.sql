use wikishare;
commit;

create table pages (
  id INTEGER NOT NULL auto_increment,
  signature VARCHAR(20) NOT NULL,
  user_id INT, 
  active_page INT NOT NULL,
  front_page INT NOT NULL,
  title VARCHAR(1000),
  content TEXT,
  timestamp timestamp,
  PRIMARY KEY (id));
commit;

create table groups (
  id INT NOT NULL auto_increment,
  name VARCHAR(50) not null,
  UNIQUE(name), PRIMARY KEY(id));

create table users (
  id INT NOT NULL auto_increment,
  group_id INT,
  name VARCHAR(50) not null,
  password varchar(30),
  timestamp timestamp,
  UNIQUE(name), 
  PRIMARY KEY(id), 
  FOREIGN KEY(group_id) REFERENCES groups(id));

create table navigations (
  id INT NOT NULL auto_increment,
  name VARCHAR(50) not null,
  content VARCHAR(1000),
  UNIQUE(name), PRIMARY KEY(id));
  
commit;