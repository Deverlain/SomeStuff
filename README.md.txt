###Project builded with Maven using Spring Boot framework.

##In order to run a project:
#1.Install Maven from  - https://maven.apache.org/download.cgi
#2.Install Spring boot - https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html
#3.Open project demo and run application.

##If all is set correctly - you will be able to open http://localhost:8080, you can run 'GET' request to check if it's working, //localhost:8080/mode for example. In order to check 'post' and 'put' requests
 you can use Postman** or Chrome Dev Tools*


*you can open it by pressing f12 in chrome browser, after open the console tab and you should be able to check all requests with #fetch command.
fetch {'/api', {method: '#METHOD NAME', headers: {'Content-Type': application/json}, body: Json.stringify({#INPUTJSON})}).then(result => console.log(result)).
It should return promise with status and proper Json.

**https://www.postman.com/