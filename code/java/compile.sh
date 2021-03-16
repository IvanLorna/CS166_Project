#! /bin/bash
rm -rf java/bin/*.class
javac -cp ".;java/lib/postgresql-42.1.4.jar;" java/src/DBproject.java -d java/bin/
