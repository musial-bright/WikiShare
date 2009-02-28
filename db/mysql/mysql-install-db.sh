#!/bin/bash

echo "-----------------------------------------------"
echo "| WikiShare DB installation script for MySQL. |"
echo "| 2009-02-28 - Adam Musial-Bright             |"
echo "-----------------------------------------------" 
echo "Install DB? [yes|no]: "
read install
if [ "$install" != "yes" ]; then
	echo "Exit."
	exit 0
fi

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