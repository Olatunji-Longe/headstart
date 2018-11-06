### About

A small web project to provide a headstart while building a java/Spring-based web app.

The following technologies were used;
- Java
- Spring Boot framework
- H2 Database 
- Thymeleaf templating
- Bootstrap
- jQuery & ajax

### Requirements
- Java 8
- Maven

### How To Run the Application?
- Clone this project 
```$xslt
    git clone https://github.com/Olatunji-Longe/headstart.git
```
- Change directory to the project root in your terminal (linux, mac) OR in your command line interface (windows PC)
```$xslt
    cd headstart
```
- build the application by executing the following command
```$xslt
    mvn clean package
```

- Run the application with the following command:
```$xslt
    mvn exec:java
```

This should launch a browser window with a login page. Use the following credentials to login;
```$xslt
    username: user@headstart.com
    password: secret
```

NOTE - This is just a default user that was used to bootstrap the application. if you need multiple users, 
you will have to include your own user registration workflow.

If you simply need to demo static users, just add them in the bootstrap phase by extending the functionality in
`com.quadbaze.headstart.lifecycle.Bootstrap.java` class's `initDefaultUserIfRequired()` method.


---

The application can be further customized as needed by inspecting the code and extending various provided functionalties according to requirements!
