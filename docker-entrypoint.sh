#!/bin/sh

PING=0

while [ $PING != 1 ] ; do
    PING=$(psql -h db -d hs_test -U hs_test -c "select 'ping';" | grep -c 'ping')
    echo 'Await DB...'
    sleep 1s
done

mv $(lein ring uberjar | sed -n 's/^Created \(.*standalone\.jar\)/\1/p') app-standalone.jar
$(java -jar app-standalone.jar)