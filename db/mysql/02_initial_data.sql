use wikishare;

INSERT INTO pages (signature, active_page, front_page, title, content, timestamp) 
	values ('s0',1,1, 'Welcome', 'Welcome to the WikiShare application.', '2009-02-19 07:04:00');

INSERT INTO pages (signature, active_page, front_page, title, content, timestamp) 
	values ('s1',1,0, 'Page not found', 'The page you requested has not been found. Pleas contact the administrator.', '2009-02-19 12:34:00');  
commit;