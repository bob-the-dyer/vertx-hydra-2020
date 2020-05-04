# vert.x way of high-availability and fail-over: pros and cons

The goal is to show the way vert.x realized high-availability and fail-over and argue pros and cons.
To build project use: `mvn clean package`

Verticals are deployed as fat jar inside Docker containers. 
To run all containers at once use: `docker-compose up` 
 
## Plan
 -  TODO
 
## Negative things to mention
 - Hazelcast by default has timeouts and delays that should be configured for each particular case.    
 - Ports will not automatically be opened while migrating from container to container, i.e. migration of web is useless,
 use super ha node with all ports needed
 - hanode - looks like quorum is set by default with -ha
 
## Hints for docker interaction:
 - To initialize docker environment run in terminal: `eval "$(docker-machine env default)`"
 - To kill all containers at once use: `docker kill $(docker ps -q)`
 - To remove all containters at once use:
    1. `docker stop $(docker ps -a -q)`
    2. `docker rm $(docker ps -a -q)`
 - To remove all none images use: `docker rmi $(docker images | grep "^<none>" | awk "{print $3}")`
 - To remove all images use:
    1. `docker rm $(docker ps -a -q)`
    2. `docker rmi $(docker images -q)`
 
## Hints for docker-compose interaction: 
 - `docker-compose up -d`
 - `docker-compose ps`
 - `docker-compose stop`
 - `docker-compose up`
 - `docker-compose scale producer=5`
    
## Hints for blockade interaction:
 - TODO