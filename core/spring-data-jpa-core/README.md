# Header

TBD

## Materials

* https://www.udemy.com/course/spring-dmdev/?couponCode=LETSLEARNNOW (46. Data JPA Starter)

## Installation

### Docker

* Install docker
* Check: docker ps

### Database (postgres)

* https://hub.docker.com/_/postgres
* Start a postgres instance: docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres
  docker run --name some-postgres -e POSTGRES_PASSWORD=pass -p 5433:5432 -d postgres
* Check if the postgres is available: docker ps

## Configure

### Idea

* Go to the Database -> Datasource -> PostgreSQL and configure the db