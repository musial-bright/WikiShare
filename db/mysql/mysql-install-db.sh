#!/bin/bash

# Install the mysql db for wiki share. 

echo --------------- Dropping wikishare db -------------------
./mysql-client.sh < 00_drop_db.sql
echo Done.
echo --------------- Creating wikishare DB -------------------
./mysql-client.sh < 01_create_db.sql
echo Done.
echo --------------- Creating tables -------------------
./mysql-client.sh < 02_create_tables.sql
echo Done.
echo --------------- Initial data -------------------
./mysql-client.sh < 03_initial_data.sql
echo Done.