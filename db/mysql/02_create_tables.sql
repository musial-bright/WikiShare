use wikishare;
commit;
create table wikipage (
  	id int not null primary key auto_increment, 
  	title varchar(1000) not null,
	content TEXT not null);
commit;