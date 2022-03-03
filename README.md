# üK 223
### Requirements
- Java JDK Version 11
- Docker Container:
    - PostgeSQL 
      - port: 5432
      - Username: postgres
      - Password: postgres

---
Final product can be found in the dev-branch, ready for deployment.
---
### Application setup
After the Container is running with the DB you can start the spring application and you should be
able to log in with either a...
- default user (username: james, pw = bond) 
- admin user (username: boss, pw = bosspw)

### User and roles
In our AppStartupRunner we have created two roles, two users and a few authorities (rights). 
####Pre defined roles and authorities:
- ROLE_ADMIN
    - CRUD for ALL users
  
- ROLE_DEFAULT
    -  CRUD for himself
    - every user can lookup other users, but will get public info only (Extra feature :D)



