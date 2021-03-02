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
- In application.properties make sure to change the user and user password to what ever user you have setup in mySQL
- Also create a schema called userdb
- Running the server should work.
- going to localhost:8080 should redirect you to the login page
- you can use:
  - email: pat@test.com
  - password: password
- you should now be able to change directories to localhost:8080/api/retrieve/value=morty@test.com which is a GET request for database data only available to logged in users
- you can then navigate to localhost:8080/logout to logout

# How Does it Work?

## Server Side 
### (com.example.server.Security) ApplicationSecurityConfig.java

This is the class that is mainly in control of the security configurations for the server (hence the name ApplicationSecurityConfig) as it extends the WebSecurityConfigurerAdapter.
This is further indicated by the @Configuration and @EnableWebSecurity annotations. 

**Overloading**
First and foremost is that this class overrides the configure method of the WebSecurityConfigurerAdapter in a number of ways this is where a lot of the security features will be configured.

The http parameterized conifgure function is where security configurations when http requests are passed to the server will be processed.

The AuthenticationManagerBuilder parameterized version of the configure function and the DaoAuthenticationProvider (this also implements the UserDetailsService) is what will allow us to use the UserService

**configure(AuthenticationManagerBuilder auth) & daoAuthenticationProvider()**
- The configure method with an AuthenticationManagerBuilder parameter is called
- The parameter is assigned an authenticationProvider
- This provider is sourced from daoAuthticationProvider function
- The daoAuthenticationProvider requires that a `passwordEncoder` be set
- The daoAuthenticationProvider requires that a `userDetailService` be set
- This daoAuthenticationProvider is then applied to the AuthenticationManagerBuilder

## (com.example.server.Security) PasswordConfig
This is primarily used for encoding passwords using bCrypt and is used in various places in the server

## (com.example.server.User) User
This is the domain class which will consist of user_id, email and password 
Because it implements UserDetails interface it must implement its methods:
- get/set UserId
- get/set Password
- get Authorities
- get Username
- isAccountNonExpired
- isAccountNonLocked
- isCredentialNonExpired
- isEnabled

Having the User domain implement UserDetails is necessary for security implementations when authorizing and authenticating the user, terrific!

## (com.example.server.User) UserRepository 
This is the interface which is linking the domain class with the ability to make changes and interact with the database. It's faanntastic.

## (com.example.server.User) UserService
This class implements the UserDetailsService which is another User service which is used for indicating the User service class to spring security to work with the UserDetails interface.

The overrided loadUserByUsername method is modified to work with my implementation of a User which is not identified by a username but an EMAIL

This class is constructed and `@Autowired` to the userRepository particularly the repository with the `@Qualifier ('database')`

This service class will handle communications between the database and the server

The 

## (com.example.server.User) UserController
Will handle Http method requests to the server indicated by `@RestController`, `@RequestMapping("/api")` and `@CrossOrigin` annotation.
The cross origin annotation in particular determins how interactions with services on different ports and hosts is handled.

Contains the a basic login post mapping which would be sent to the service class creating a user

Contains a get mapping which should only be available to logged in users 

## TBD
- Im still trying to figure out how to setup a custom login UI from a react client which will sent the user informaiton to the appropriate endpoint that spring security uses. Right now, when running the server, and accessing it over a browser you will be redirected to the spring securities built in UI login page.