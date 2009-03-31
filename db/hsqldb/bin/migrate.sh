#!/bin/sh

# Create new wiki share database.
# Warning! All tables will be deleted!

echo "------------------------------------"
echo "| WikiShare                         |"
echo "| Copyright Adam Musial-Bright      |"
echo "| 2008-09-23                        |"
echo "------------------------------------"

echo "Deleting tables..."
sh hsqldbExecute.sh ../sql/00_*
echo "Done."

echo "Creating tables..." 
sh hsqldbExecute.sh ../sql/01_*
echo "Done."

echo "Creating initial data..."
sh hsqldbExecute.sh ../sql/02_*
echo "Done."

#echo "Inserting test data..." 
#echo "### Uncomment this, if you want to skip the test data. ###"
#sh hsqldbExecute.sh ../sql/03_*
#echo "Done."
