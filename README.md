# cloud-parking

## Run Database
docker run --name parking-db -p 5432:5432 -e POSTGRES_DB=parking -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123 -d postgres:10-alpine

## Stop Docker
docker stop parking-db

## Start Docker
docker start parking-db