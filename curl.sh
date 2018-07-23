#!/bin/bash

###
### It is best to stop the app, delete "h2.mv.db" from the root and restarting the app before running this; in order to reduce information noise.
###

echo -e "Create User\n"

## Create User
curl -X "POST" "http://localhost:8080/api/user" \
     -H 'Content-Type: application/json; charset=utf-8' \
     -d $'{
  "last_name": "Doe",
  "username": "John.Doe",
  "first_name": "John"
}'

echo -e "\n\nUpdate User\n"

## Update User
curl -X "PUT" "http://localhost:8080/api/user/1" \
     -H 'Content-Type: application/json; charset=utf-8' \
     -d $'{
  "username": "John.Doe",
  "first_name": "John",
  "last_name": "Doe The Second"
}'

echo -e "\nGet User\n"

## Get User
curl "http://localhost:8080/api/user/1"

echo -e "\n\nList Users\n"

## List Users
curl "http://localhost:8080/api/user"

echo -e "\n\nCreate Tasks\n"

## Create Task
for i in {1..10}
do curl -X "POST" "http://localhost:8080/api/user/1/task" \
     -H 'Content-Type: application/json; charset=utf-8' \
     -d $'{
  "name": "Task Name",
  "date_time": "2017-12-25 13:45:22",
  "description": "Task Description"
}';
done

echo -e "Update Task\n"

## Update Task
curl -X "PUT" "http://localhost:8080/api/user/1/task/1" \
     -H 'Content-Type: application/json; charset=utf-8' \
     -d $'{
  "name": "Task Name (Updated)",
  "date_time": "2017-12-20 13:45:22",
  "description": "Task Description (Updated)"
}'

echo -e "\nGet Task\n"

## Get Task
curl "http://localhost:8080/api/user/1/task/1"

echo -e "\n\nList Tasks\n"

## List Tasks
curl "http://localhost:8080/api/user/1/task"

echo -e "\n\nDelete Task\n"

## Delete Task
curl -X "DELETE" "http://localhost:8080/api/user/1/task/10" \
     -H 'Content-Type: application/json; charset=utf-8' \
     -d $'{
  "username": "john.doe2",
  "first_name": "John",
  "last_name": "Doe"
}'

echo -e "\n"

## Continuously check tasks
## Create Task
while :
do curl "http://localhost:8080/api/user/1/task";
echo -e "\n\n";
sleep 0.25;
done