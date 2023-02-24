#!/bin/bash
declare schema="public" #change here
echo "$schema"
for filename in /docker-entrypoint-initdb.d/*.sql; do
  sed -i -e "s/@cdmDatabaseSchema/$schema/g" "$filename"
done