#!/bin/sh

java \
   -cp ../lib/hsqldb.jar \
   org.hsqldb.util.ScriptTool \
   -driver org.hsqldb.jdbcDriver \
   -url jdbc:hsqldb:hsql: \
   -database //localhost/wikishare \
   -user sa \
   -script $1 \
