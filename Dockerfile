FROM openjdk:17-oracle
LABEL maintainer="drdrosal@mymail.mapua.edu.ph"
WORKDIR /opt/app
EXPOSE 8080
COPY target/guitardb-1.0-SNAPSHOT.jar /opt/app/guitardb.jar
ENTRYPOINT {"java","-jar","guitardb.jar"}
