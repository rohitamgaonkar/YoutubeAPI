Steps to Setup

1) Clone the repository

Command -
git clone "github http repo path"

2) Data base h2 - comsole -
http://localhost:9999/h2

3) Run the app using maven
Command -
mvn spring-boot:run

4)
  Application can be accessed at `http://localhost:9999`.
---------------------------------------------------------------------------------------
Package run the jar file :

Command -
mvn clean package
java -jar target/YoutubeVideoAPI-0.0.1-SNAPSHOT.jar

-----------------------------------------------------------------------------------------
1) Present setup is for h2 database

2) In case Database - Postgres/mysql need to be used , please update application.properties.
Replace username , password , url and dialect etc ., for mysql or Postgres.
Refer for mysql - https://spring.io/guides/gs/accessing-data-mysql/


