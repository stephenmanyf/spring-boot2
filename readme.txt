spring boot version is required to be 2.1.2, the version later on would cause the unit tests do not work
Spring security once added to pom.xml, all resources will be required authorization, and return 401 error status
POM.xml line 1 unknown error is resolved by changing the spring boot version to 2.1.2

hello world reference: https://www.mkyong.com/spring-boot/spring-rest-hello-world-example/
validation reference: https://www.mkyong.com/spring-boot/spring-rest-validation-example/ 
						https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
security reference: https://www.mkyong.com/spring-boot/spring-rest-spring-security-example/
					https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-security
					https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/
					https://spring.io/guides/topicals/spring-security-architecture
security with JPA reference: https://www.onlinetutorialspoint.com/spring-boot/spring-boot-security-mysql-database-integration-example.html
			Worth to check: https://www.concretepage.com/spring-boot/spring-boot-security-rest-jpa-hibernate-mysql-crud-example
Logging reference: Logging Reference: https://www.baeldung.com/spring-boot-logging
Swallow unexpected exception: http://www.voidcn.com/article/p-prjyyzeo-bd.html
			
			
TODO:
- Login attempt limit (Reference: https://www.mkyong.com/spring-security/spring-security-limit-login-attempts-example/)
- Session in Redis
- JWT
- OAuth2 and traditional authentication altogether
- Logout
x)- Log4j or SLF4J
- Change context path


To package a jar file:
$ mvn package

To run the jar directly:
$ java -jar /Users/StephenMan/Development/Workspace/Tutorial/mkyong-tutorial/new-spring-boot/spring-boot-demo2/target/app.jar