#!/bin/bash
#You need to install the jq library in order to make this bash work -> https://stedolan.github.io/jq/download/
edit_files(){
   for filename in "$1"*.json; do
        jq 'walk(if type == "object" then with_entries( if .key == "id" then .value = "##ignore" else . end ) else . end)' "$filename"  > "$filename".tmp && mv "$filename".tmp "$filename"
        jq 'walk(if type == "object" then with_entries( if .key == "idAsLong" then .value = "##ignore" else . end ) else . end)' "$filename"  > "$filename".tmp && mv "$filename".tmp "$filename"
        jq 'walk(if type == "object" then with_entries( if .key == "factId1" then .value = "##ignore" else . end ) else . end)' "$filename"  > "$filename".tmp && mv "$filename".tmp "$filename"
        jq 'walk(if type == "object" then with_entries( if .key == "factId2" then .value = "##ignore" else . end ) else . end)' "$filename"  > "$filename".tmp && mv "$filename".tmp "$filename"
        jq 'walk(if type == "object" then with_entries( if .key == "person" then .value = "##ignore" else . end ) else . end)' "$filename"  > "$filename".tmp && mv "$filename".tmp "$filename"
    done
}

for folder in ./json/output/medical_data/*/*/ ; do
  edit_files "$folder"
done

for folder in ./json/output/person_data/ ; do
  edit_files "$folder"
done


