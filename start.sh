docker-compose down 

docker build -t back-end:latest ./demo

docker build -t front-end:latest ./imagelite


docker-compose up --build --force-recreate --remove-orphans

docker run -p 3000:3000