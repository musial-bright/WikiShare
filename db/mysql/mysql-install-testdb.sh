#!/bin/bash

echo "----------------------------------------------------"
echo "| WikiShare test DB installation script for MySQL. |"
echo "| 2009-04-29 - Adam Musial-Bright                  |"
echo "----------------------------------------------------" 
echo "Install test DB? [yes|no]: "
read install
if [ "$install" != "yes" ]; then
	echo "Exit."
	exit 0
fi

echo --------------- Creating wikishare DB -------------------
./mysql-client.sh < 10_create_testdb.sql 
echo Done.
echo --------------- Creating tables -------------------
./mysql-client.sh < 11_create_tables_testdb.sql 
echo Done.
