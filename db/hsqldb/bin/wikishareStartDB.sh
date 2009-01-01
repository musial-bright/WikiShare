#!/bin/sh

java -cp ../lib/hsqldb.jar org.hsqldb.Server -database.0 file:../data/wikishare -dbname.0 wikishare 
##>> ../log/wikishare.log &

