# Install the mysql db for wiki share. 

echo --------------- Dropping wikishare db -------------------
mysql -u root < 00_drop_db.sql
echo Done.
echo --------------- Creating wikishare DB -------------------
mysql -u root < 01_create_db.sql
echo Done.
echo --------------- Creating tables -------------------
mysql -u root < 02_create_tables.sql
echo Done.