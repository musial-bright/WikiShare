#!/bin/bash

# Install the mysql db for wiki share. 

echo --------------- Dropping wikishare db -------------------
sh mysql-db.sh < 00_drop_db.sql
echo Done.
echo --------------- Creating wikishare DB -------------------
sh mysql-db.sh < 01_create_db.sql
echo Done.
echo --------------- Creating tables -------------------
sh mysql-db.sh < 02_create_tables.sql
echo Done.