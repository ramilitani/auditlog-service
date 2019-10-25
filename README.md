# auditlog-service

This application aims to audit and store in MongoDB all actions that a 
client can perform while using the systems.
When a client request any API, there is a Filter that gets all 
information from the request and queues that data in ActiveMQ.
<br /><br />
For this purpose two generic users were previously created to test the API
from Postman, for example.
Theses users are created when the application is started and stored in the H2 database.
The script that creates theses users is in the data.sql file in the application resources folder.
<br /><br />
In addition, the application manages the user session through Spring Session. 
Therefore, for all request, except the login request, you must pass an x-auth-token in the request header.
This token is received when you call the login api.


__Requirements__

  The project must have  the follow tools installed to run:
  - JAVA 8
  - ActiveMQ
  - MongoDB
  - Maven
<br/><br/>

__Steps to run the project__
  
  1. Start the MongoDB
  
  2. Start the ActiveMQ. In the bin folder of the ActiveMQ, type the follow command to run the ActiveMQ
      ```bash
         ./activemq start
      ```
     
  3. Go to the folder where the project is located, and build the application by Maven
      ```bash
           mvn clean install
      ```
  4. Run the follow command in the target folder of the Java project
        ```bash
             java -jar auditlog-service-1.0-SNAPSHOT.jar
        ```
  <br/><br/>
  __Call the API__
  
  1. First you must to log in the application
  
        ```bash
          curl -d '{"login":"test@gmail.com", "password":"test"}' -H "Content-Type: application/json" -X POST localhost:8080/auditlog/login
        ```
     You should receive a Response like this:
     ```bash
       {"sessaoId":"85370f2e-a320-4bd1-b814-f5ccfb7705a3","login":"test@gmail.com","userId":1}
     ```
     The sessaoId is the token you must put in the header for every next requests you make.
     
  2. To show the audit messages stored in MongoDB for a specific user you should use the follow request:
     ```bash
       curl -H "x-auth-token: a8c7c2ad-5265-42e6-bcdd-a2295363cbef" "localhost:8080/auditlog/audit/1?page=0&size=10"
     ```
     The url is this: localhost:8080/auditlog/audit/{userId}?page=0&size=10
     
  3. To show all the audit messages stored in MongoDB you should use the follow request;
     ```bash
       curl -H "x-auth-token: a8c7c2ad-5265-42e6-bcdd-a2295363cbef" "localhost:8080/auditlog/audit?page=0&size=10"
     ```
  4. For the logoff of the application, follow the command above: 
     ```bash
       curl -H "x-auth-token: a8c7c2ad-5265-42e6-bcdd-a2295363cbef" "localhost:8080/auditlog/logout"
      ``` 
     