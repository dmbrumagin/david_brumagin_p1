# david_brumagin_p1

## Overview
A REST API for an expense reimbursement system. The system will will allow the company to track expenses and analyze spending. 

## Bussiness Rules
- All expenses have a single employee as the issuer
- Expenses start as pending and must then be approved or denied
  - Once approved or denied they CANNOT be deleted or edited
- Negative expenses are not allowed

## Routes

### Employee Routes
- POST /employees 
  - returns a 201
- GET /employees
- GET /employees/120
  - returns a 404 if employee not found
- PUT /employees/150
  - returns a 404 if employee not found
- DELETE /employees/190
  - returns a 404 if employee not found


### Expenses routes
- POST /expenses 
  - returns a 201
- GET /expenses
- GET /expenses?status=pending
  - also can get status approved or denied
- GET /expenses/12
  - returns a 404 if expense not found
- PUT /expenses/15
  - returns a 404 if expense not found
- PATCH /expenses/20/approve
  - returns a 404 if expense not found
- PATCH /expenses/20/deny
  - returns a 404 if expense not found
- DELETE /expenses/19
  - returns a 404 if car not found

It is common for REST routes to be nested 
- GET /employees/120/expenses
  - returns expenses for employee 120
- POST /employees/120/expenses
  - adds an expense to employee 120

## ORM
### Unimplemented in deployed version

Uses reflection to obtain information about the entities used in object relational mapping.
Uses a helper class that maps the entities in the project so they can be obtained after type erasure in the generic ORM.

interface CrudDAO<T>{
    //use reflection to read the annotations on the class
    // to try and implement the default interface methods

    default T createEntity(T entity){

    }; // creates an entity

    default T getEntityById(int id){
        
    }; // Get an entity by ID

    default List<T> getAllEntities(){

    };// get all instances of the entity

    default T updateEntity(T entity){

    };// update an instance 


## Technical
- Use of a custom logger for logging
- Javalin is used for the API layer

## Testing
- JUnit tests for all DAO methods
- Postman test for each endpoint
- Stub implementations to test services

## Deployment
- The app is containerized and on dockerhub https://hub.docker.com/r/dbrumagin/project1/
- The app is deployed on an EC2 for your presentation http://expense.eba-9m4h5zyu.us-east-1.elasticbeanstalk.com/
- The database is PostgreSQL on an AWS RDS
