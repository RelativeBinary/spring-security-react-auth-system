# spring-security-react-auth-system

This is a small program which I'm working on that would demonstraight a basic user login system.
- User can login
- User is authenticated and given a jwt token
- jwt token is used to authorize API requests

# General Idea of the Process
So far I've only got the server side working so that:
- There is a database which holds emails and passwords
- The passwords are encoded using bCrypt
- The server accepts login requests and is able to authenticate and authorize the user
- The user only after loggin in can make API requests

# Setting Up and Using the Project
- Open the server folder into IntelliJ IDEA
- Right-click on the pom.xml in /src folder and reload the maven dependencies
- Make sure you are running java 11
- Running the server should work.
- going to localhost:8080 should redirect you to the login page
- you can use:
  - email: pat@test.com
  - password: password
- you should now be able to change directories to localhost:8080/api/retrieve/value=morty@test.com which is a GET request for database data only available to logged in users
- you can then navigate to localhost:8080/logout to logout
