# vert.x approach for high-availability and fail-over: pros and cons

The goal is to demonstrate vert.x approach for building distributed highly-available systems with automatic fail-over and argue pros and cons.
 
## Plan
 - Self-Introduction
 - Plan announcement & goals statement: 
    - Intro to vert.x tool
    - Focus on vert.x way to implement HA and fail-over 
    - Several attempts to use vert.x HA and fail-over with obstacles and solutions 
    - Arguing on pros and cons
    - Recommendations
 - Essential intro to vert.x: 
   - Reactive
   - High-performant, high-load oriented
   - Based on netty/NIO 
   - Non-blocking and async APIs 
   - Actor Model
   - Microservices natively
   - Polyglot-programming for JVM 
   - No JVM low-level concurrency   
 - vert.x fundamentals:
   - Verticals (actors)
   - Eventbus (messages exchange)
   - Cluster
 - vert.x approach to high-availability (ha) and fail-over:
   - Approach essentials: failure detection and fail-over mechanics
   - Comparison to classical Actor Model supervision and [akka](https://doc.akka.io/docs/akka/2.5/general/supervision.html)   
   - Enabling ha
 - Main use case:
   - source system -> source-connector -> converter -> destination-connector -> destination system
   - Essential code base presentation (IDEA) 
 - Demos (IDEA, terminals):   
   1. obstacle 1: failing-over while verticle byte code is not in the place  
   1. solution 1.1: hanode approach 
   1. solution 1.2: custom classloader approach (???)
   1. obstacle 2: split-brain with blockade 
   1. solution 2: split-brain with blockade with quorum
   1. obstacle 3: poison pill verticle   
   1. solution 3 + solution 1.3: poison pill isolation in hagroup 
 - Conclusions
 - Q/A
   
## Build hints

To build project use: `./mvnw clean package`

Verticals are deployed as fat jar inside Docker containers.
 
To run all containers at once use: `docker-compose -f docker-compose.yml up` 
 
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
 
## Negative things to mention
 - Hazelcast by default has timeouts and delays that should be configured for each particular case.    
 - Ports will not automatically be opened while migrating from container to container, i.e. migration of web is useless,
 use super ha node with all ports needed
 - hanode - looks like quorum is set by default with -ha