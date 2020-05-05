# vert.x approach for high-availability and fail-over: pros and cons

The goal is to demonstrate vert.x approach for building highly-available distributed systems with fail-over and argue pros and cons.
To build project use: `./mvnw clean package`

Verticals are deployed as fat jar inside Docker containers. 
To run all containers at once use: `docker-compose -f docker-compose.yml up` 
 
## Plan
 - Essential intro to vert.x: 
   - reactive
   - high-performant
   - based on netty/nio
   - non-blocking and async 
   - actor model 
   - microservices
   - polyglotic  
 - vert.x fundamentals:
   - verticals
   - eventbus
   - cluster
 - vert.x approach to high-availability and fail-over
   - enabling ha
   - detection
   - fail-over
 - demo:
   - failure: failing over while code is not there
   - success: hanode approach
   - emulating split-brain with blockade without quorum: fail
   - emulating split-brain with blockade with quorum: fail
   - success: no hanode approach
   - failure: poisoning verticle  
   - success: poisoning verticle isolation in group

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
 - 'docker-compose logs -f'
    
## Hints for blockade interaction:
 - `blockade --config blockade-ha.yml up`
 - `blockade destroy`
 - `blockade status`
 - `blockade slow con`
 - `blockade fast con`
 - `blockade flaky src con`
 - `blockade duplicate src`
 - `blockade partition dst`
 - `blockade partition src,con con,dst`
 - `blockade blockade random-partition`
 - `blockade join`
 - [more details](https://github.com/worstcase/blockade#commands)
 - [and more details](https://blockade.readthedocs.io/en/latest/)
 