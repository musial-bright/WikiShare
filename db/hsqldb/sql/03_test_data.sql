INSERT INTO pages (signature, active_page, front_page, title, content, timestamp) 
	values ('2',0,1,'Wikipage 1', 'Here some content 1', '2007-07-15 07:04:00'); 
INSERT INTO pages (signature, active_page, front_page, title, content, timestamp) 
	values ('2',0,0,'Wikipage 2', 'Here some content 2', '2008-07-03 12:34:00'); 
INSERT INTO pages (signature, active_page, front_page, title, content, timestamp) 
	values ('2',1,0,'Wikipage 3', 'Here some content 3', '2008-08-24 13:34:00'); 
	
INSERT INTO groups (name) values ('admin');

INSERT INTO users (group_id, name, password, timestamp) 
	values (0, 'admin', '', '2008-09-14 13:34:00');
	
INSERT INTO users (group_id, name, password, timestamp) 
	values (0, 'adam', '', '2008-09-14 13:34:00');
